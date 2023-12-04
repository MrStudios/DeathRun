package pl.mrstudios.deathrun.api.arena.pad;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface ITeleportPad {
    @NotNull Location padLocation();
    @NotNull Location teleportLocation();
}
