package pl.mrstudios.deathrun.api.arena.event.arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;

public class ArenaShutdownStartedEvent extends Event {

    private final @NotNull IArena arena;

    private boolean defaultProcedure;

    public ArenaShutdownStartedEvent(
            @NotNull IArena arena,
            boolean defaultProcedure
    ) {
        this.arena = arena;
        this.defaultProcedure = defaultProcedure;
    }

    public @NotNull IArena getArena() {
        return this.arena;
    }

    public boolean isDefaultProcedure() {
        return this.defaultProcedure;
    }

    public void setDefaultProcedure(boolean defaultProcedure) {
        this.defaultProcedure = defaultProcedure;
    }

    /* Handler List */
    private static final HandlerList HANDLERS = new HandlerList();

    /* Setter */
    public void defaultProcedure(boolean value) {
        this.defaultProcedure = value;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
