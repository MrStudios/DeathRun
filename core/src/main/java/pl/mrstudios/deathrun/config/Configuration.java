package pl.mrstudios.deathrun.config;

import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.config.impl.LanguageConfiguration;
import pl.mrstudios.deathrun.config.impl.MapConfiguration;
import pl.mrstudios.deathrun.config.impl.PluginConfiguration;

public record Configuration(
        @NotNull PluginConfiguration plugin,
        @NotNull LanguageConfiguration language,
        @NotNull MapConfiguration map
) {}
