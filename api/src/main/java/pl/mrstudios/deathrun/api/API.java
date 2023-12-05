package pl.mrstudios.deathrun.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;

@Getter
public class API {

    private final IArena arena;
    private final ITrapRegistry trapRegistry;

    private final String version;

    public API(IArena arena, ITrapRegistry trapRegistry, PluginDescriptionFile pluginDescriptionFile) {

        setInstance(this);

        this.arena = arena;
        this.trapRegistry = trapRegistry;
        this.version = pluginDescriptionFile.getVersion();

    }

    @Setter(AccessLevel.PRIVATE)
    public static @NotNull API instance;

}
