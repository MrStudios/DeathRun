package pl.mrstudios.deathrun.api.arena.event.arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.user.IUser;

public class ArenaUserLeftEvent extends Event {

    private final @NotNull IUser user;
    private final @NotNull IArena arena;

    public ArenaUserLeftEvent(
            @NotNull IUser user,
            @NotNull IArena arena
    ) {
        this.user = user;
        this.arena = arena;
    }

    public @NotNull IUser getUser() {
        return this.user;
    }

    public @NotNull IArena getArena() {
        return this.arena;
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
