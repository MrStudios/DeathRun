package pl.mrstudios.deathrun.api.arena.event.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;

@Getter
@AllArgsConstructor
public class ArenaTrapActivateEvent extends Event implements Cancellable {

    private @NotNull IArena arena;
    private @NotNull ITrap trap;

    @Getter @Setter
    private boolean cancelled;

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
