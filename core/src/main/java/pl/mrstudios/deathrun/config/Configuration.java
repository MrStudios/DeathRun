package pl.mrstudios.deathrun.config;

import pl.mrstudios.deathrun.config.impl.LanguageConfiguration;
import pl.mrstudios.deathrun.config.impl.MapConfiguration;
import pl.mrstudios.deathrun.config.impl.PluginConfiguration;

public record Configuration(
        PluginConfiguration plugin,
        LanguageConfiguration language,
        MapConfiguration map
) {}
