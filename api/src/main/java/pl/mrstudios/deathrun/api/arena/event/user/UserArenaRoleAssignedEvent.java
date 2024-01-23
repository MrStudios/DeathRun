package pl.mrstudios.deathrun.api.arena.event.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

public class UserArenaRoleAssignedEvent extends Event {

    private final @NotNull Role role;
    private final @NotNull IUser user;

    public UserArenaRoleAssignedEvent(
            @NotNull Role role,
            @NotNull IUser user
    ) {
        this.role = role;
        this.user = user;
    }

    public @NotNull Role getRole() {
        return this.role;
    }

    public @NotNull IUser getUser() {
        return this.user;
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
