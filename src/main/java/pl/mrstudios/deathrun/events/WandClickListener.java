package pl.mrstudios.deathrun.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.utils.MessageUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class WandClickListener implements Listener {

    private Location positionOne, positionTwo;

    @Getter @Setter
    private static WandClickListener instance;

    public WandClickListener() {
        setInstance(this);
    }

    @EventHandler
    public void onWandInteractEvent(PlayerInteractEvent event) {

        try {

            if (Objects.isNull(event.getPlayer().getInventory().getItemInHand()) || !event.getPlayer().getInventory().getItemInHand().getType().equals(Material.GOLD_AXE))
                return;

            Location location = new Location(event.getClickedBlock().getWorld().getName(), event.getClickedBlock().getLocation().getBlockX(), event.getClickedBlock().getLocation().getBlockY(), event.getClickedBlock().getLocation().getBlockZ(), event.getClickedBlock().getLocation().getPitch(), event.getClickedBlock().getLocation().getYaw());

            switch (event.getAction()) {

                case LEFT_CLICK_BLOCK: {

                    SoundUtil.playSound(event.getPlayer(), "ORB_PICKUP");
                    event.getPlayer().sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aPosition #1 was set. &2&l*").color().getMessage());

                    this.positionOne = location;
                    break;

                }

                case RIGHT_CLICK_BLOCK: {

                    SoundUtil.playSound(event.getPlayer(), "ORB_PICKUP");
                    event.getPlayer().sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aPosition #2 was set. &2&l*").color().getMessage());

                    this.positionTwo = location;
                    break;

                }

                default:
                    break;

            }

            event.setCancelled(true);

        } catch (Exception ignored) {}

    }

    public static WandClickListener instance() { return instance; }

    public List<Location> getLocations() {

        List<Location> locations = new ArrayList<>();

        if (Objects.isNull(positionOne) || Objects.isNull(positionTwo))
            return locations;

        int maxX = (int) Math.max(positionOne.getX(), positionTwo.getX()),
                minX = (int) Math.min(positionOne.getX(), positionTwo.getX()),
                maxY = (int) Math.max(positionOne.getY(), positionTwo.getY()),
                minY = (int) Math.min(positionOne.getY(), positionTwo.getY()),
                maxZ = (int) Math.max(positionOne.getZ(), positionTwo.getZ()),
                minZ = (int) Math.min(positionOne.getZ(), positionTwo.getZ());

        for (double x = minX; x <= maxX; x++)
            for (double y = minY; y <= maxY; y++)
                for (double z = minZ; z <= maxZ; z++)
                    locations.add(new Location(positionOne.getWorld(), x, y, z, 0, 0));

        return locations;

    }

}
