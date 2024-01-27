package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.Arena;

import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.STARTING;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.WAITING;

public class ArenaPlayerJoinKickListener implements Listener {

    private final Arena arena;

    @Inject
    public ArenaPlayerJoinKickListener(
            @NotNull Arena arena
    ) {
        this.arena = arena;
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerKick(PlayerLoginEvent event) {

        if (event.getResult() == KICK_FULL)
            if (event.getPlayer().hasPermission("mrstudios.deathrun.admin"))
                event.allow();

    }

    @Deprecated
    @EventHandler(priority = MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {

        if (this.arena.getGameState() != WAITING && this.arena.getGameState() != STARTING)
            event.disallow(KICK_FULL, "");

        if (event.getResult() == KICK_FULL)
            if (this.arena.getGameState() == WAITING || this.arena.getGameState() == STARTING)
                return;

        if (event.getPlayer().hasPermission("mrstudios.deathrun.admin"))
            event.allow();

    }

}
