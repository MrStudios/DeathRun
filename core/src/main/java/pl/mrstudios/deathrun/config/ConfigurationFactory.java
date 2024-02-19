package pl.mrstudios.deathrun.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.config.serdes.PluginSerdes;

import java.io.File;
import java.nio.file.Path;

import static eu.okaeri.configs.ConfigManager.create;

public record ConfigurationFactory(
        @NotNull Path directory
) {

    public <CONFIG extends OkaeriConfig> CONFIG produce(@NotNull Class<CONFIG> clazz, @NotNull String file) {
        return produce(clazz, new File(this.directory.toFile(), file));
    }

    public <CONFIG extends OkaeriConfig> CONFIG produce(@NotNull Class<CONFIG> clazz, @NotNull File file) {
        return create(clazz, (initializer) ->
                initializer.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new PluginSerdes())
                        .withBindFile(file)
                        .saveDefaults()
                        .load(true)
        );
    }


}
