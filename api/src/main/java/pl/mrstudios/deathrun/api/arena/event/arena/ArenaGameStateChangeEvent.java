package pl.mrstudios.deathrun.api.arena.event.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.enums.GameState;

@Getter
@AllArgsConstructor
public class ArenaGameStateChangeEvent extends Event {

    private IArena arena;
    private GameState gameState;

    /* Handler List */
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

}
