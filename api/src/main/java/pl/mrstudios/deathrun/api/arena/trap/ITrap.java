package pl.mrstudios.deathrun.api.arena.trap;

import org.bukkit.Location;

import java.time.Duration;
import java.util.List;

import static org.jetbrains.annotations.ApiStatus.Internal;

public interface ITrap {

    @Internal
    void setId(String id);

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
