package pl.mrstudios.deathrun.arena.data.checkpoint;

import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.data.checkpoint.ICheckpoint;

import java.util.List;

public record Checkpoint(int id, List<Location> locations) implements ICheckpoint {}
