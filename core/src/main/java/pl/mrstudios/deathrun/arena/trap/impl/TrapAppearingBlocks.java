package pl.mrstudios.deathrun.arena.trap.impl;

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

public class TrapAppearingBlocks extends Trap {

    @Serializable
    private Material material;

    /* Data */
    protected final Map<Location, BlockData> backup = new HashMap<>();

    public @NotNull Material getMaterial() {
        return this.material;
    }

    public void setMaterial(@NotNull Material material) {
        this.material = material;
    }

    @Override
    public void start() {

        super.locations.forEach((location) -> this.backup.put(location, location.getBlock().getBlockData()));
        super.locations.forEach((location) -> location.getBlock().setType(this.material));

    }

    @Override
    public void end() {

        this.backup.forEach((key, value) -> key.getBlock().setBlockData(value));
        this.backup.clear();

    }

    @Override
    public @NotNull Location getButton() {
        return super.button;
    }

    @Override
    public void setButton(@NotNull Location location) {
        super.button = location;
    }

    @Override
    public @NotNull List<Location> getLocations() {
        return super.locations;
    }

    @Override
    public void setLocations(@NotNull List<Location> locations) {
        super.locations = locations;
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
        return list;
    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ofSeconds(3);
    }

}
