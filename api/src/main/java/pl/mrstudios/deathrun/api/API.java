package pl.mrstudios.deathrun.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;

@Getter
public class API {

    private final IArena arena;
    private final ITrapRegistry trapRegistry;

    public API(IArena arena, ITrapRegistry trapRegistry) {

        setInstance(this);

        this.arena = arena;
        this.trapRegistry = trapRegistry;

    }

    @Setter(AccessLevel.PRIVATE)
    public static API instance;

}
