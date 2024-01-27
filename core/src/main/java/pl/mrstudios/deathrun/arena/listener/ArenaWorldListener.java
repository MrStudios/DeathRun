package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;

import static org.bukkit.event.EventPriority.MONITOR;

public class ArenaWorldListener implements Listener {

    @Inject
    public ArenaWorldListener() {}

    @EventHandler(priority = MONITOR)
    public void onArenaWorldLoad(@NotNull WorldLoadEvent event) {
        event.getWorld().setAutoSave(false);
    }

    @EventHandler(priority = MONITOR)
    public void onWorldUnload(@NotNull WorldUnloadEvent event) {
        event.getWorld().setAutoSave(false);
    }

    @EventHandler(priority = MONITOR)
    public void onWorldSave(@NotNull WorldSaveEvent event) {
        event.getWorld().setAutoSave(false);
    }

}
