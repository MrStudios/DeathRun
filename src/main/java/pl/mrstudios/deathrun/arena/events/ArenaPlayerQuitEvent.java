package pl.mrstudios.deathrun.arena.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.utils.MessageUtil;

public class ArenaPlayerQuitEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        User user = Main.instance().getArena().getUser(event.getPlayer());

        if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING) && !Main.instance().getArena().getGameState().equals(GameState.ENDING))
            event.setQuitMessage(new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getPlayerLeft()
                    .replace("<player>", event.getPlayer().getName())
                    .replace("<currentPlayers>", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("<maxPlayers>", String.valueOf(Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxPlayers())
                    )).color().getMessage());
        else
            event.setQuitMessage("");

        Main.instance().getArena().getPlayers().remove(user);
        Main.instance().getArena().getRunners().remove(user);
        Main.instance().getArena().getDeaths().remove(user);
        Main.instance().getArena().getSpectators().remove(user);


    }

}
