package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.arena.checkpoint.Checkpoint;

import java.util.Collections;
import java.util.List;

@Header({
        " ",
        "--------------------------------------------------------------------------",
        "                                INFORMATION",
        "--------------------------------------------------------------------------",
        " ",
        " Please dont modify this file, any modifications may cause problems with",
        " plugin, modify only if you know what you are doing.",
        " ",
        "--------------------------------------------------------------------------",
        " "
}) @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MapConfiguration extends OkaeriConfig {

    public String arenaName;

    /* Spawns */
    public Location arenaWaitingLobbyLocation;

    public List<Location> arenaRunnerSpawnLocations = Collections.emptyList();
    public List<Location> arenaDeathSpawnLocations = Collections.emptyList();

    /* Traps */
    public List<ITrap> arenaTraps = Collections.emptyList();

    /* Checkpoints */
    public List<Checkpoint> arenaCheckpoints = Collections.emptyList();

    /* Misc */
    public List<Location> arenaStartBarrierBlocks = Collections.emptyList();

    /* Setup Status */
    public boolean arenaSetupEnabled = true;

}
