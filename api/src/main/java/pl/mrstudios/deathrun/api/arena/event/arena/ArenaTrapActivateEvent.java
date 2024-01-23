package pl.mrstudios.deathrun.api.arena.event.arena;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;

public class ArenaTrapActivateEvent extends Event implements Cancellable {

    private final @NotNull ITrap trap;
    private final @NotNull IArena arena;
    private boolean cancelled;

    public ArenaTrapActivateEvent(
            @NotNull ITrap trap,
            @NotNull IArena arena
    ) {
        this.trap = trap;
        this.arena = arena;
        this.cancelled = false;
    }

    public @NotNull ITrap getTrap() {
        return this.trap;
    }

    public @NotNull IArena getArena() {
        return this.arena;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean value) {
        this.cancelled = value;
    }

    /* Handler List */
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
