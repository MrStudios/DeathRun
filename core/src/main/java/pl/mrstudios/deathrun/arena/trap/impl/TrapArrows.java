package pl.mrstudios.deathrun.arena.trap.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.arena.trap.Trap;

import java.time.Duration;
import java.util.List;

public class TrapArrows extends Trap {

    @Override
    public void start() {

        super.locations.stream()
                .map(Location::getBlock)
                .forEach((block) -> {

                    BlockFace blockFace = ((Directional) block.getBlockData()).getFacing();
                    if (block.getRelative(blockFace).getType().isSolid())
                        return;

                    block.getLocation().getWorld().spawnArrow(
                            block.getRelative(blockFace).getLocation().toCenterLocation(),
                            block.getRelative(blockFace).getRelative(blockFace).getLocation().toVector().subtract(block.getRelative(blockFace).getLocation().toVector()),
                            1.5f, 1
                    );
                });

    }

    @Override
    public void end() {}

    @Override
    public void setExtra(Object... objects) {}

    @Override
    public @NotNull List<Location> filter(@NotNull List<Location> list, @Nullable Object... objects) {
        return list.stream()
                .filter((location) -> location.getBlock().getType() == Material.DISPENSER)
                .toList();
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
    public @NotNull Duration getDuration() {
        return Duration.ZERO;
    }

}
