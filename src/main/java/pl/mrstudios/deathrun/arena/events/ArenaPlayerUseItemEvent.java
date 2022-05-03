package pl.mrstudios.deathrun.arena.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.utils.ChannelUtil;

public class ArenaPlayerUseItemEvent implements Listener {

    @EventHandler
    public void onInteractWithItem(PlayerInteractEvent event) {

        if (event.getPlayer().getItemInHand() == null)
            return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        User user = Main.instance().getArena().getUser(event.getPlayer());

        switch (event.getPlayer().getItemInHand().getType()) {

            case BED: {
                ChannelUtil.writeMessageAtChannel(user, "BungeeCord", "Connect", Main.instance().getConfiguration().getSettings().getServer().getLobbyServer());
                break;
            }

            default:
                break;

        }

        event.setCancelled(true);

    }

}
