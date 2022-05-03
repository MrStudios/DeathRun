package pl.mrstudios.deathrun.arena.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.utils.MessageUtil;

public class ArenaPlayerLoginEvent implements Listener {

    @EventHandler
    public void onPlayerPreLogin(PlayerPreLoginEvent event) {

        if (Main.instance().getArena().getPlayers().size() >= Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxPlayers()) {
            event.setKickMessage(new MessageUtil(Main.instance().getConfiguration().getMessages().getKick().getServerIsFull()).color().getMessage());
            event.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }

        if (Main.instance().getArena().getGameState().equals(GameState.PLAYING) || Main.instance().getArena().getGameState().equals(GameState.ENDING)) {
            event.setKickMessage(new MessageUtil(Main.instance().getConfiguration().getMessages().getKick().getGameInProgress()).color().getMessage());
            event.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }

    }

}
