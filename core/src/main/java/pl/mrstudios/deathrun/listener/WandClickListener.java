package pl.mrstudios.deathrun.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.data.annotations.RegistrableListener;

import java.util.ArrayList;
import java.util.List;

@RegistrableListener(register_on_setup = true)
public class WandClickListener implements Listener {

    private static Location POSITION_ONE, POSITION_TWO;
    public static List<Location> LOCATIONS = new ArrayList<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {

        if (event.getItem() == null)
            return;

        if (!event.getItem().getType().equals(Material.GOLDEN_AXE))
            return;

        if (event.getClickedBlock() == null)
            return;

        switch (event.getAction()) {

            case LEFT_CLICK_BLOCK -> {
                POSITION_ONE = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage(API.INSTANCE.VERSION.serialize("&r &6&l» &7Position &6#1 &7has been set."));
            }

            case RIGHT_CLICK_BLOCK -> {
                POSITION_TWO = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage(API.INSTANCE.VERSION.serialize("&r &6&l» &7Position &6#2 &7has been set."));
            }

        }

        if (POSITION_ONE != null && POSITION_TWO != null) {

            int maxX = (int) Math.max(POSITION_ONE.getX(), POSITION_TWO.getX()),
                    minX = (int) Math.min(POSITION_ONE.getX(), POSITION_TWO.getX()),
                    maxY = (int) Math.max(POSITION_ONE.getY(), POSITION_TWO.getY()),
                    minY = (int) Math.min(POSITION_ONE.getY(), POSITION_TWO.getY()),
                    maxZ = (int) Math.max(POSITION_ONE.getZ(), POSITION_TWO.getZ()),
                    minZ = (int) Math.min(POSITION_ONE.getZ(), POSITION_TWO.getZ());

            LOCATIONS.clear();
            for (double x = minX; x <= maxX; x++)
                for (double y = minY; y <= maxY; y++)
                    for (double z = minZ; z <= maxZ; z++)
                        LOCATIONS.add(new Location(POSITION_ONE.getWorld(), x, y, z, 0, 0));

        }

        event.setCancelled(true);

    }

}
