package pl.mrstudios.deathrun.api.arena.interfaces;

import org.bukkit.Location;

import java.time.Duration;
import java.util.List;

public interface ITrap {

    String getId();

    Location getButton();
    void setButton(Location location);

    List<Location> getLocations();
    void setLocations(List<Location> locations);

    void setExtra(Object... objects);

    void start();
    void end();

    Duration getDuration();

}
