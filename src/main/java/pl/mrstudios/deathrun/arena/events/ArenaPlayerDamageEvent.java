package pl.mrstudios.deathrun.arena.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.User;

public class ArenaPlayerDamageEvent implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) return;

        User user = Main.instance().getArena().getUser(((Player) event.getEntity()).getPlayer());

        if (Main.instance().getArena().getGameState().equals(GameState.WAITING) || Main.instance().getArena().getGameState().equals(GameState.STARTING) || user.getRole().equals(Role.SPECTATOR) || user.getRole().equals(Role.DEATH))
            event.setCancelled(true);

        if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING) || !user.getRole().equals(Role.RUNNER))
            return;

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL)
            user.respawn();
        else if (event.getEntity().getFallDistance() >= 10)
            user.respawn();

        event.getEntity().setFireTicks(0);
        event.setCancelled(true);

    }

}
