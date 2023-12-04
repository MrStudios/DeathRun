package pl.mrstudios.deathrun.arena.pad;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.pad.ITeleportPad;

public record TeleportPad(@NotNull Location padLocation, @NotNull Location teleportLocation) implements ITeleportPad {}
