package pl.mrstudios.deathrun.api.arena.checkpoint;

import org.bukkit.Location;

import java.util.List;

public interface ICheckpoint {

    int id();
    List<Location> locations();

}
