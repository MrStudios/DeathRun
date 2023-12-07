package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.stream.Stream;

@ArenaRegistrableListener
public class ArenaTeleportPadListener implements Listener {

    private final Configuration configuration;

    @Inject
    public ArenaTeleportPadListener(Configuration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    public void onPlayerEnterPlate(PlayerInteractEvent event) {

        if (event.getAction() != Action.PHYSICAL)
            return;

        if (event.getClickedBlock() == null)
            return;

        if (Stream.of(this.slabs).noneMatch((material) -> material == event.getClickedBlock().getType()))
            return;

        this.configuration.map().teleportPads
                .stream()
                .filter((teleportPad) -> teleportPad.padLocation().getBlockX() == event.getClickedBlock().getX() && teleportPad.padLocation().getBlockY() == event.getClickedBlock().getY() && teleportPad.padLocation().getBlockZ() == event.getClickedBlock().getZ())
                .findFirst()
                .ifPresent((teleportPad) -> event.getPlayer().teleport(teleportPad.teleportLocation()));

    }

    protected final Material[] slabs = {
            Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
            Material.ACACIA_PRESSURE_PLATE,
            Material.BIRCH_PRESSURE_PLATE,
            Material.CRIMSON_PRESSURE_PLATE,
            Material.DARK_OAK_PRESSURE_PLATE,
            Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE,
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Material.OAK_PRESSURE_PLATE,
            Material.SPRUCE_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE,
            Material.WARPED_PRESSURE_PLATE
    };

}
