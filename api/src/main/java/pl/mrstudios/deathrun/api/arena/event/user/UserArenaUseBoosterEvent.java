package pl.mrstudios.deathrun.api.arena.event.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.booster.IBooster;
import pl.mrstudios.deathrun.api.arena.user.IUser;

public class UserArenaUseBoosterEvent extends Event {

    private final @NotNull IUser user;
    private final @NotNull IArena arena;
    private final @NotNull IBooster booster;

    public UserArenaUseBoosterEvent(
            @NotNull IUser user,
            @NotNull IArena arena,
            @NotNull IBooster booster
    ) {
        this.user = user;
        this.arena = arena;
        this.booster = booster;
    }

    public @NotNull IUser getUser() {
        return this.user;
    }

    public @NotNull IArena getArena() {
        return this.arena;
    }

    public @NotNull IBooster getBooster() {
        return this.booster;
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
