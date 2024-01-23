package pl.mrstudios.deathrun.api;

import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;

public class API {

    private final @NotNull IArena arena;
    private final @NotNull ITrapRegistry trapRegistry;

    private final @NotNull String version;

    public API(
            @NotNull IArena arena,
            @NotNull ITrapRegistry trapRegistry,
            @NotNull PluginDescriptionFile pluginDescriptionFile
    ) {

        setInstance(this);

        this.arena = arena;
        this.trapRegistry = trapRegistry;
        this.version = pluginDescriptionFile.getVersion();

    }

    public @NotNull IArena getArena() {
        return arena;
    }

    public @NotNull ITrapRegistry getTrapRegistry() {
        return trapRegistry;
    }

    public @NotNull String getVersion() {
        return version;
    }

    public static @NotNull API instance;

    protected static void setInstance(@NotNull API api) {
        instance = api;
    }

}
