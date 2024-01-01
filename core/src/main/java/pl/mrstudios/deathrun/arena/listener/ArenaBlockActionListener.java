package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;

import java.util.Arrays;

@ArenaRegistrableListener
public class ArenaBlockActionListener implements Listener {

    @Inject
    public ArenaBlockActionListener() {}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (event.getClickedBlock() != null)
                if (Arrays.stream(this.materials).anyMatch((material) -> material == event.getClickedBlock().getType()))
                    event.setCancelled(true);

        if (event.getAction() == Action.PHYSICAL)
            event.setCancelled(true);

    }

    protected final Material[] materials = {
            Material.CHEST,
            Material.DISPENSER,
            Material.DROPPER,
            Material.FURNACE,
            Material.HOPPER,
            Material.BREWING_STAND,
            Material.BEACON,
            Material.ANVIL,
            Material.CHIPPED_ANVIL,
            Material.DAMAGED_ANVIL,
            Material.ENCHANTING_TABLE,
            Material.ENDER_CHEST,
            Material.BARREL,
            Material.BLAST_FURNACE,
            Material.SMOKER
    };

}
