package pl.mrstudios.deathrun.arena.pad;

import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.pad.ITeleportPad;

public record TeleportPad(Location padLocation, Location teleportLocation) implements ITeleportPad {}
