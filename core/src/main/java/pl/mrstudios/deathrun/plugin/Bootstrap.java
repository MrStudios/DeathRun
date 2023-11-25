package pl.mrstudios.deathrun.plugin;

import com.sk89q.worldedit.WorldEdit;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mrstudios.commons.inject.Injector;
import pl.mrstudios.commons.reflection.Reflections;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.arena.trap.TrapRegistry;
import pl.mrstudios.deathrun.arena.trap.impl.TrapAppearingBlocks;
import pl.mrstudios.deathrun.arena.trap.impl.TrapDisappearingBlocks;
import pl.mrstudios.deathrun.arena.trap.impl.TrapTNT;
import pl.mrstudios.deathrun.commands.CommandDeathRun;
import pl.mrstudios.deathrun.commands.handler.InvalidCommandUsageHandler;
import pl.mrstudios.deathrun.commands.handler.NoCommandPermissionsHandler;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.config.ConfigurationFactory;
import pl.mrstudios.deathrun.config.impl.LanguageConfiguration;
import pl.mrstudios.deathrun.config.impl.MapConfiguration;
import pl.mrstudios.deathrun.config.impl.PluginConfiguration;
import pl.mrstudios.deathrun.exception.MissingDependencyException;

@SuppressWarnings("all")
public class Bootstrap extends JavaPlugin {

    private IArena arena;
    private ITrapRegistry trapRegistry;


    private MiniMessage miniMessage;
    private BukkitAudiences audiences;

    private Configuration configuration;
    private ConfigurationFactory configurationFactory;

    private Injector injector;
    private WorldEdit worldEdit;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {

        /* Dependency Check */
        if (!this.getServer().getPluginManager().isPluginEnabled("WorldEdit"))
            throw new MissingDependencyException("You must have WorldEdit (v7.2.9+) installed on your server to use this plugin.");

        /* World Edit */
        this.worldEdit = WorldEdit.getInstance();

        /* Configuration */
        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        this.configuration = new Configuration(
                this.configurationFactory.produce(PluginConfiguration.class, "config.yml"),
                this.configurationFactory.produce(LanguageConfiguration.class, "language.yml"),
                this.configurationFactory.produce(MapConfiguration.class, "map.yml")
        );

        /* Kyori */
        this.audiences = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .build();

        /* Create Arena Instance */
        this.arena = new Arena();

        /* Trap Registry */
        this.trapRegistry = new TrapRegistry();

        /* Initialize Injector */
        this.injector = new Injector()

                /* Bukkit */
                .register(Plugin.class, this)
                .register(Server.class, this.getServer())

                /* Kyori */
                .register(MiniMessage.class, this.miniMessage)
                .register(BukkitAudiences.class, this.audiences)

                /* World Edit */
                .register(WorldEdit.class, this.worldEdit)

                /* Plugin Stuff */
                .register(IArena.class, this.arena)
                .register(ITrapRegistry.class, this.trapRegistry)
                .register(Configuration.class, this.configuration);

        /* Register Traps */ // TODO: annotation reflection
        this.trapRegistry.register("TRAP_TNT", TrapTNT.class);
        this.trapRegistry.register("TRAP_APPEARING_BLOCKS", TrapAppearingBlocks.class);
        this.trapRegistry.register("TRAP_DISAPPEARING_BLOCKS", TrapDisappearingBlocks.class);

        /* Register Commands */
        this.liteCommands = LiteCommandsBukkit.builder()

                /* Settings */
                .settings((settings) -> settings.nativePermissions(false))

                /* Handler */
                .invalidUsage(this.injector.inject(InvalidCommandUsageHandler.class))
                .missingPermission(this.injector.inject(NoCommandPermissionsHandler.class))

                /* Commands */
                .commands(LiteCommandsAnnotations.of(
                        this.injector.inject(CommandDeathRun.class)
                ))

                /* Schematic */
                .schematicGenerator(SchematicFormat.angleBrackets())

                /* Suggesters */
                .argumentSuggester(String.class, ArgumentKey.of("type"), SuggestionResult.of(this.trapRegistry.list()))

                /* Build */
                .build();

        /* Register Listeners */
        if (!this.configuration.map().arenaSetupEnabled)
        new Reflections<Listener>("pl.mrstudios.deathrun")
                .getClassesAnnotatedWith(ArenaRegistrableListener.class)
                .forEach((listener) -> this.injector.inject(listener));

        /* Initialize API */
        new API(
                this.arena, this.trapRegistry
        );

    }

    @Override
    public void onDisable() {

        if (this.audiences != null)
            this.audiences.close();

    }

}
