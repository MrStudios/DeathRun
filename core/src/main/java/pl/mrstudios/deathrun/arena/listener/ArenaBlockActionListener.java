package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;

import java.util.Arrays;

import static org.bukkit.Material.*;
import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class ArenaBlockActionListener implements Listener {

    @Inject
    public ArenaBlockActionListener() {}

    @EventHandler(priority = MONITOR)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {

        if (event.getAction() == RIGHT_CLICK_BLOCK)
            if (event.getClickedBlock() != null)
                if (Arrays.stream(this.materials).anyMatch((material) -> material == event.getClickedBlock().getType()))
                    event.setCancelled(true);

        if (event.getAction() == Action.PHYSICAL)
            event.setCancelled(true);

    }

    protected final Material[] materials = {
            CHEST, DISPENSER, DROPPER, FURNACE,
            HOPPER, BREWING_STAND, BEACON, ANVIL,
            CHIPPED_ANVIL, DAMAGED_ANVIL, ENCHANTING_TABLE,
            ENDER_CHEST, BARREL, BLAST_FURNACE, SMOKER
    };

}
