package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.stream.Stream;

import static org.bukkit.Material.*;
import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.event.block.Action.PHYSICAL;

public class ArenaTeleportPadListener implements Listener {

    private final Configuration configuration;

    @Inject
    public ArenaTeleportPadListener(
            @NotNull Configuration configuration
    ) {
        this.configuration = configuration;
    }

    @EventHandler(priority = MONITOR)
    public void onPlayerEnterPlate(@NotNull PlayerInteractEvent event) {

        if (event.getAction() != PHYSICAL)
            return;

        if (event.getClickedBlock() == null)
            return;

        if (Stream.of(this.plates).noneMatch((material) -> material == event.getClickedBlock().getType()))
            return;

        this.configuration.map().teleportPads
                .stream().filter(
                        (teleportPad) -> teleportPad.padLocation().getBlockX() == event.getClickedBlock().getX()
                                && teleportPad.padLocation().getBlockY() == event.getClickedBlock().getY()
                                && teleportPad.padLocation().getBlockZ() == event.getClickedBlock().getZ()
                ).findFirst().ifPresent(
                        (teleportPad) -> event.getPlayer().teleport(teleportPad.teleportLocation())
                );

    }

    protected final Material[] plates = {
            POLISHED_BLACKSTONE_PRESSURE_PLATE, ACACIA_PRESSURE_PLATE,
            BIRCH_PRESSURE_PLATE, CRIMSON_PRESSURE_PLATE,
            DARK_OAK_PRESSURE_PLATE, HEAVY_WEIGHTED_PRESSURE_PLATE,
            JUNGLE_PRESSURE_PLATE, LIGHT_WEIGHTED_PRESSURE_PLATE,
            OAK_PRESSURE_PLATE, SPRUCE_PRESSURE_PLATE,
            STONE_PRESSURE_PLATE, WARPED_PRESSURE_PLATE
    };

}
