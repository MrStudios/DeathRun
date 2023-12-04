package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;

@ArenaRegistrableListener
public class ArenaPlayerJoinKickListener implements Listener {

    @Inject
    public ArenaPlayerJoinKickListener() {}

    @EventHandler
    public void onPlayerKick(PlayerLoginEvent event) {

        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL)
            if (event.getPlayer().hasPermission("mrstudios.deathrun.admin"))
                event.allow();

    }

}
