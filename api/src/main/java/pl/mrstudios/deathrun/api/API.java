package pl.mrstudios.deathrun.api;

import lombok.AccessLevel;
import lombok.Setter;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;

public class API {

    public IArena arena;
    public ITrapRegistry trapRegistry;

    public API(IArena arena, ITrapRegistry trapRegistry) {

        setInstance(this);

        this.arena = arena;
        this.trapRegistry = trapRegistry;

    }

    @Setter(AccessLevel.PRIVATE)
    public static API instance;

}
