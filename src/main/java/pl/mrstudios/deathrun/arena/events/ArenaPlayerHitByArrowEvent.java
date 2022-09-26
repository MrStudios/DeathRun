package pl.mrstudios.deathrun.arena.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.arena.traps.TrapArrows;

public class ArenaPlayerHitByArrowEvent implements Listener {
    

    @EventHandler
    public void onPlayerHitByArrow(EntityDamageByEntityEvent e) {
        
        if (!(e.getEntity() instanceof Player) || ! (e.getDamager() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) e.getDamager();

        if (! (arrow.getShooter() instanceof TrapArrows)) {
            return;
        }

        User user = Main.instance().getArena().getUser(((Player) e.getEntity()).getPlayer());
        GameState state = Main.instance().getArena().getGameState();

        if (state == GameState.PLAYING && user.getRole() == Role.RUNNER) {
            user.respawn();
        }
    }
}
