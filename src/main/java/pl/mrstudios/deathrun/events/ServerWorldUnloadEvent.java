package pl.mrstudios.deathrun.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

public class ServerWorldUnloadEvent implements Listener {

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {

        event.setCancelled(event.getWorld().isAutoSave());

        if (event.isCancelled())
            Bukkit.getServer().unloadWorld(event.getWorld(), false);

    }

}
