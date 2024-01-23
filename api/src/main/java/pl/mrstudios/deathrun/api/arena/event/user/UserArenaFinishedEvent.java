package pl.mrstudios.deathrun.api.arena.event.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.user.IUser;

public class UserArenaFinishedEvent extends Event {

    private final @NotNull IUser user;
    private final int time;
    private final int position;

    public UserArenaFinishedEvent(
            @NotNull IUser user,
            int time,
            int position
    ) {
        this.user = user;
        this.time = time;
        this.position = position;
    }

    public @NotNull IUser getUser() {
        return this.user;
    }

    public int getTime() {
        return this.time;
    }

    public int getPosition() {
        return this.position;
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
