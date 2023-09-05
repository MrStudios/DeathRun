package pl.mrstudios.deathrun.api.arena.event.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.enums.Role;
import pl.mrstudios.deathrun.api.arena.interfaces.IUser;

@Getter
@AllArgsConstructor
public class UserArenaRoleAssignedEvent extends Event {

    private IUser user;
    private Role role;

    /* Handler List */
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}
