package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;

import static org.bukkit.event.EventPriority.MONITOR;

public class ArenaProjectileListener implements Listener {

    @Inject
    public ArenaProjectileListener() {}

    @EventHandler(priority = MONITOR)
    public void onProjectileLand(@NotNull ProjectileHitEvent event) {
        event.getEntity().remove();
    }

}
