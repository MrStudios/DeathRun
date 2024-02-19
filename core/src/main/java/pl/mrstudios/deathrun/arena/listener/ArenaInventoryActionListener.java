package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;

import static org.bukkit.event.EventPriority.MONITOR;

public class ArenaInventoryActionListener implements Listener {

    @Inject
    public ArenaInventoryActionListener() {}

    @EventHandler(priority = MONITOR)
    public void onInventoryClick(@NotNull InventoryClickEvent event) {

        if (event.getClickedInventory() == null)
            return;

        if (event.getClickedInventory().getType() != InventoryType.PLAYER)
            return;

        event.setCancelled(true);

    }

    @EventHandler(priority = MONITOR)
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerItemSwap(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerArrowPickup(PlayerPickupArrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerItemPickup(PlayerAttemptPickupItemEvent event) {
        event.setCancelled(true);
    }

}
