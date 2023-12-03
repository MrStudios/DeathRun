package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;

@ArenaRegistrableListener
public class ArenaWorldListener implements Listener {

    @Inject
    public ArenaWorldListener() {}

    @EventHandler
    public void onArenaWorldLoad(WorldLoadEvent event) {
        event.getWorld().setAutoSave(false);
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        event.getWorld().setAutoSave(false);
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        event.getWorld().setAutoSave(false);
    }

}
