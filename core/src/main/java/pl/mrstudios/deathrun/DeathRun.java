package pl.mrstudios.deathrun;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.data.trap.impl.TrapAppearingBlocks;
import pl.mrstudios.deathrun.arena.data.trap.impl.TrapDisappearingBlocks;
import pl.mrstudios.deathrun.commands.CommandDeathRun;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.config.ConfigurationFactory;
import pl.mrstudios.deathrun.config.impl.LanguageConfiguration;
import pl.mrstudios.deathrun.config.impl.MapConfiguration;
import pl.mrstudios.deathrun.config.impl.PluginConfiguration;
import pl.mrstudios.deathrun.data.annotations.RegistrableListener;
import pl.mrstudios.deathrun.data.reflections.Reflections;
import pl.mrstudios.deathrun.data.version.VersionSupportCommon;

import java.util.List;

@Getter @Setter
public class DeathRun extends JavaPlugin {

    /* Data */
    private ConfigurationFactory configurationFactory;

    @Override
    @SneakyThrows
    public void onEnable() {

        /* Initialize API */
        API.INSTANCE = new API();

        /* Set Version Support */
        API.INSTANCE.VERSION = new VersionSupportCommon();

        /* Load Configuration */
        this.configurationFactory = new ConfigurationFactory(this.getDataFolder());
        Configuration.PLUGIN = this.configurationFactory.produce(PluginConfiguration.class, "config.yml");
        Configuration.LANGUAGE = this.configurationFactory.produce(LanguageConfiguration.class, "language.yml");
        Configuration.MAP = this.configurationFactory.produce(MapConfiguration.class, "map.yml");

        /* Register Command */
        this.getCommand("deathrun").setExecutor(new CommandDeathRun());

        /* Register Listeners */
        new Reflections("pl.mrstudios.deathrun")
                .getClassesAnnotatedWith(RegistrableListener.class)
                .forEach((listener) -> {

                    if (listener.getAnnotation(RegistrableListener.class).register_on_setup() && !Configuration.MAP.ARENA_SETUP_ENABLED)
                        return;

                    try {
                        this.getServer().getPluginManager().registerEvents((Listener) listener.getDeclaredConstructor().newInstance(), this);
                    } catch (Exception ignored) {}

                });

        /* Register Traps */
        List.of(
                new TrapAppearingBlocks(),
                new TrapDisappearingBlocks()
        ).forEach(API.INSTANCE::registerTrap);

        /* Create Arena Instance */
        API.INSTANCE.ARENA = new Arena();

    }

    @Override
    public void onDisable() {}

}
