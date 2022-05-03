package pl.mrstudios.deathrun.arena.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.utils.MessageUtil;

public class ArenaServerPingEvent implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {

        event.setMaxPlayers(Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxPlayers());
        event.setMotd(new MessageUtil("&r &7Map: &f" + Main.instance().getArena().getMapConfig().getName() + "\n&r &7State: &f" + Main.instance().getArena().getGameState()).color().getMessage());

    }

}
