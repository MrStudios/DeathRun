package pl.mrstudios.deathrun.api.arena.event.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;

@Getter
@AllArgsConstructor
public class ArenaShutdownStartedEvent extends Event {

    private @NotNull IArena arena;
    private boolean defaultProcedure;

    /* Handler List */
    private static final HandlerList HANDLERS = new HandlerList();

    /* Setter */
    public void defaultProcedure(boolean value) {
        this.defaultProcedure = value;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
