package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;

@ArenaRegistrableListener
public class ArenaProjectileListener implements Listener {

    @Inject
    public ArenaProjectileListener() {}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onProjectileLand(ProjectileHitEvent event) {
        event.getEntity().remove();
    }

}
