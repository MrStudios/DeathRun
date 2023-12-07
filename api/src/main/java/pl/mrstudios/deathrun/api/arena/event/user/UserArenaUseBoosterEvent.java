package pl.mrstudios.deathrun.api.arena.event.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.booster.IBooster;
import pl.mrstudios.deathrun.api.arena.user.IUser;

@Getter
@AllArgsConstructor
public class UserArenaUseBoosterEvent extends Event {

    private @NotNull IUser user;
    private @NotNull IArena arena;
    private @NotNull IBooster booster;

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
