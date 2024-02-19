package pl.mrstudios.deathrun.arena.trap;

import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;

import java.util.List;

public abstract class Trap implements ITrap {
    public Location button;
    public List<Location> locations;
}
