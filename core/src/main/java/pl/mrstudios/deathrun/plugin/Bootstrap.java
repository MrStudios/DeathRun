package pl.mrstudios.deathrun.plugin;

import com.sk89q.worldedit.WorldEdit;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mrstudios.commons.inject.Injector;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.commons.reflection.Reflections;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.ArenaServiceRunnable;
import pl.mrstudios.deathrun.arena.trap.TrapRegistry;
import pl.mrstudios.deathrun.arena.trap.impl.TrapAppearingBlocks;
import pl.mrstudios.deathrun.arena.trap.impl.TrapArrows;
import pl.mrstudios.deathrun.arena.trap.impl.TrapDisappearingBlocks;
import pl.mrstudios.deathrun.arena.trap.impl.TrapTNT;
import pl.mrstudios.deathrun.command.handler.InvalidCommandUsageHandler;
import pl.mrstudios.deathrun.command.handler.NoCommandPermissionsHandler;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.config.ConfigurationFactory;
import pl.mrstudios.deathrun.config.impl.LanguageConfiguration;
import pl.mrstudios.deathrun.config.impl.MapConfiguration;
import pl.mrstudios.deathrun.config.impl.PluginConfiguration;
import pl.mrstudios.deathrun.exception.MissingDependencyException;
import pl.mrstudios.deathrun.util.ZipUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;

@SuppressWarnings("all")
public class Bootstrap extends JavaPlugin {

    private Arena arena;
    private TrapRegistry trapRegistry;


    private MiniMessage miniMessage;
    private BukkitAudiences audiences;
    private BungeeComponentSerializer bungeeComponentSerializer;

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
        this.configurationFactory = new ConfigurationFactory(this.getDataFolder().toPath());
        this.configuration = new Configuration(
                this.configurationFactory.produce(PluginConfiguration.class, "config.yml"),
                this.configurationFactory.produce(LanguageConfiguration.class, "language.yml"),
                this.configurationFactory.produce(MapConfiguration.class, "map.yml")
        );

        /* Kyori */
        this.audiences = BukkitAudiences.create(this);
        this.bungeeComponentSerializer = BungeeComponentSerializer.get();
        this.miniMessage = MiniMessage.builder()
                .build();

        /* Create Arena Instance */
        this.arena = new Arena(this.configuration.map().arenaName);

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
                .register(BungeeComponentSerializer.class, this.bungeeComponentSerializer)

                /* World Edit */
                .register(WorldEdit.class, this.worldEdit)

                /* Plugin Stuff */
                .register(Arena.class, this.arena)
                .register(TrapRegistry.class, this.trapRegistry)
                .register(Configuration.class, this.configuration);

        /* Register Traps */
        Arrays.asList(
                TrapTNT.class,
                TrapAppearingBlocks.class,
                TrapDisappearingBlocks.class,
                TrapArrows.class
        ).forEach(this.trapRegistry::register);

        /* Register Commands */
        this.liteCommands = LiteCommandsBukkit.builder()

                /* Settings */
                .settings((settings) -> settings.nativePermissions(false))

                /* Handler */
                .invalidUsage(this.injector.inject(InvalidCommandUsageHandler.class))
                .missingPermission(this.injector.inject(NoCommandPermissionsHandler.class))

                /* Commands */
                .commands(LiteCommandsAnnotations.of(
                        new Reflections<>("pl.mrstudios.deathrun")
                                .getClassesAnnotatedWith(Command.class)
                                .stream().map(this.injector::inject)
                                .filter(Objects::nonNull)
                                .toArray(Object[]::new)
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
                    .getClassesImplementing(Listener.class).stream().filter(
                            (listener) -> stream(listener.getConstructors())
                                    .anyMatch((constructor) -> constructor.isAnnotationPresent(Inject.class))
                    ).forEach(
                            (listener) -> this.getServer().getPluginManager()
                                    .registerEvents(this.injector.inject(listener), this)
                    );

        /* Start Arena Service */
        if (!this.configuration.map().arenaSetupEnabled)
            this.injector.inject(ArenaServiceRunnable.class)
                    .runTaskTimer(this, 0, 20);

        /* Initialize API */
        new API(this.arena, this.trapRegistry, this.getDescription());

        /* Set Max Players */
        if (!this.configuration.map().arenaSetupEnabled)
            this.getServer().setMaxPlayers(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size());

        /* Register Channel */
        if (!this.configuration.map().arenaSetupEnabled)
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    @Override
    public void onDisable() {

        if (this.audiences != null)
            this.audiences.close();

    }

    @Override
    public void onLoad() {

        try {

            Stream.of(new File(this.getDataFolder(), "backup").listFiles())
                    .filter((file) -> file.getName().endsWith(".zip"))
                    .forEach((file) -> {

                        long startTime = currentTimeMillis();
                        this.getLogger().info("Restoring " + file.getName().replace(".zip", "") + " world backup.");

                        try {

                            File worldFile = new File(file.getName().replace(".zip", ""));
                            FileUtils.deleteDirectory(worldFile);

                            ZipUtil.unzip(file, worldFile.toPath());
                            this.getLogger().info("Restoring " + file.getName().replace(".zip", "") + " world backup complete. [" + (currentTimeMillis() - startTime) + "ms]");

                        } catch (Exception exception) {
                            this.getLogger().severe("Restoring " + file.getName().replace(".zip", "") + " world backup failed. [" + (currentTimeMillis() - startTime) + "ms]");
                        }

                    });

        } catch (Exception ignored) {}

    }

}
