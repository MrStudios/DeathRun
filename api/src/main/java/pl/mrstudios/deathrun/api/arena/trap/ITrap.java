package pl.mrstudios.deathrun.api.arena.trap;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.List;

public interface ITrap {

    @NotNull Location getButton();
    void setButton(@NotNull Location location);

    @NotNull List<Location> getLocations();
    void setLocations(@NotNull List<Location> locations);

    void setExtra(@Nullable Object... objects);
    @NotNull List<Location> filter(@NotNull List<Location> locations, @Nullable Object... objects);

    void start();
    void end();

    @NotNull Duration getDuration();

}
