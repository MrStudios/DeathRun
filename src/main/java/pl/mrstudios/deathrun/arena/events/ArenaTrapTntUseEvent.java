package pl.mrstudios.deathrun.arena.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Objects;

public class ArenaTrapTntUseEvent implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        if (!Objects.equals(event.getEntity().getType(), EntityType.PRIMED_TNT))
            return;

        event.blockList().clear();

    }

    @EventHandler
    public void onEntityIgnite(BlockIgniteEvent event) {

        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.EXPLOSION))
            event.setCancelled(true);

    }

}
