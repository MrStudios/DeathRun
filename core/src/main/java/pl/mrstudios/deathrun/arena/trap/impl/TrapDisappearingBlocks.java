package pl.mrstudios.deathrun.arena.trap.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import pl.mrstudios.deathrun.api.arena.trap.annotations.Serializable;
import pl.mrstudios.deathrun.arena.trap.Trap;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@Getter @Setter
public class TrapDisappearingBlocks extends Trap {

    @Serializable
    private Material material;

    /* Data */
    private Map<Location, BlockData> backup = Collections.emptyMap();

    public TrapDisappearingBlocks() {
        super("TRAP_DISAPPEARING_BLOCKS");
    }

    @Override
    public void start() {

        super.getLocations()
                .stream()
                .filter((location) -> location.getBlock().getType() == material)
                .forEach((location) -> {
                    this.backup.put(location, location.getBlock().getBlockData());
                    location.getBlock().setType(Material.AIR);
                });

    }

    @Override
    public void end() {

        this.backup.forEach((key, value) -> key.getBlock().setBlockData(value));
        this.backup.clear();

    }

    @Override
    public void setExtra(Object... objects) {

        if (objects.length == 0)
            return;

        if (objects[0] instanceof Material extraMaterial)
            this.material = extraMaterial;

    }

    @Override
    public Duration getDuration() {
        return Duration.ofSeconds(3);
    }

}
