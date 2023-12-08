package pl.mrstudios.deathrun.arena.trap.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.trap.annotations.Serializable;
import pl.mrstudios.deathrun.arena.trap.Trap;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class TrapAppearingBlocks extends Trap {

    @Serializable
    private Material material;

    /* Data */
    protected final Map<Location, BlockData> backup = new HashMap<>();

    @Override
    public void start() {

        super.getLocations().forEach((location) -> this.backup.put(location, location.getBlock().getBlockData()));
        super.getLocations().forEach((location) -> location.getBlock().setType(this.material));

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

        if (objects[0] instanceof Material fillableMaterial)
            this.material = fillableMaterial;

    }

    @Override
    public @NotNull List<Location> filter(@NotNull List<Location> list, @Nullable Object... objects) {

        if (objects == null)
            return list;

        if (objects.length == 2)
            return list;

        if (!(objects[1] instanceof Material filterMaterial))
            return list;

        return list.stream()
                .filter((location) -> location.getBlock().getType() == filterMaterial)
                .toList();

    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ofSeconds(3);
    }

}
