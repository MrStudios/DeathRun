package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;

@ArenaRegistrableListener
public class ArenaPlayerJoinKickListener implements Listener {

    private final Arena arena;

    @Inject
    public ArenaPlayerJoinKickListener(Arena arena) {
        this.arena = arena;
    }

    @EventHandler
    public void onPlayerKick(PlayerLoginEvent event) {

        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL)
            if (event.getPlayer().hasPermission("mrstudios.deathrun.admin"))
                event.allow();

    }

    @Deprecated
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        if (this.arena.getGameState() != GameState.WAITING && this.arena.getGameState() != GameState.STARTING)
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, "");

        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL)
            if (this.arena.getGameState() == GameState.WAITING || this.arena.getGameState() == GameState.STARTING)
                return;

        if (event.getPlayer().hasPermission("mrstudios.deathrun.admin"))
            event.allow();

    }

}
