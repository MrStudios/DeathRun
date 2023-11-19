package pl.mrstudios.deathrun.arena.checkpoint;

import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;

import java.util.List;

public record Checkpoint(int id, List<Location> locations) implements ICheckpoint {}
