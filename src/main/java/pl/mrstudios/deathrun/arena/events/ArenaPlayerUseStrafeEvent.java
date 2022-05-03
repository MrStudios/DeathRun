package pl.mrstudios.deathrun.arena.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.Strafe;
import pl.mrstudios.deathrun.arena.objects.User;

public class ArenaPlayerUseStrafeEvent implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        try {

            if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING))
                return;

            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK))
                return;

            User user = Main.instance().getArena().getUser(event.getPlayer());
            Strafe.Type type = Strafe.Type.valueOf(event.getPlayer().getItemInHand().getItemMeta().getLore().get(0));

            Strafe.getStrafes().get(type).boost(user);

        } catch (Exception ignored) {}

    }

}
