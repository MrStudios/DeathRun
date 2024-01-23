package pl.mrstudios.deathrun.api.arena.event.arena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.enums.GameState;

public class ArenaGameStateChangeEvent extends Event {

    private final @NotNull IArena arena;
    private final @NotNull GameState gameState;

    public ArenaGameStateChangeEvent(
            @NotNull IArena arena,
            @NotNull GameState gameState
    ) {
        this.arena = arena;
        this.gameState = gameState;
    }

    public @NotNull IArena getArena() {
        return this.arena;
    }

    public @NotNull GameState getGameState() {
        return this.gameState;
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
