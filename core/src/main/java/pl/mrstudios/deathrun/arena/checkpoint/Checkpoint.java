package pl.mrstudios.deathrun.arena.checkpoint;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;

import java.util.List;

public record Checkpoint(
        int id,
        @NotNull Location spawn,
        @NotNull List<Location> locations
) implements ICheckpoint {}
