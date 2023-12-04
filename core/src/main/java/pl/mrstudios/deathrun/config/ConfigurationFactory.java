package pl.mrstudios.deathrun.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import pl.mrstudios.deathrun.config.serdes.PluginSerdes;

import java.io.File;

public record ConfigurationFactory(File directory) {

    public <T extends OkaeriConfig> T produce(Class<T> clazz, String file) {
        return produce(clazz, new File(this.directory, file));
    }

    public <T extends OkaeriConfig> T produce(Class<T> clazz, File file) {
        return ConfigManager.create(clazz, (initializer) ->
                initializer.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new PluginSerdes())
                        .withBindFile(file)
                        .saveDefaults()
                        .load(true)
        );
    }


}
