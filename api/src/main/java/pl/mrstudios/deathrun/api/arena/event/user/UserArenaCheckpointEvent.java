package pl.mrstudios.deathrun.api.arena.event.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.api.arena.user.IUser;

public class UserArenaCheckpointEvent extends Event {

    private final @NotNull IUser user;
    private final @NotNull ICheckpoint checkpoint;

    public UserArenaCheckpointEvent(
            @NotNull IUser user,
            @NotNull ICheckpoint checkpoint
    ) {
        this.user = user;
        this.checkpoint = checkpoint;
    }

    public @NotNull IUser getUser() {
        return this.user;
    }

    public @NotNull ICheckpoint getCheckpoint() {
        return this.checkpoint;
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
