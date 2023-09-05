package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.interfaces.ITrap;
import pl.mrstudios.deathrun.arena.data.checkpoint.Checkpoint;

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
})
public class MapConfiguration extends OkaeriConfig {

    @CustomKey("arena_name")
    public String ARENA_NAME;

    /* Spawns */
    @CustomKey("arena_waiting_lobby_location")
    public Location ARENA_WAITING_LOBBY_LOCATION;

    @CustomKey("arena_runner_spawn_locations")
    public List<Location> ARENA_RUNNER_SPAWN_LOCATIONS = Collections.emptyList();

    @CustomKey("arena_death_spawn_locations")
    public List<Location> ARENA_DEATH_SPAWN_LOCATIONS = Collections.emptyList();

    /* Traps */
    @CustomKey("arena_traps")
    public List<ITrap> ARENA_TRAPS = Collections.emptyList();

    /* Checkpoints */
    @CustomKey("arena_checkpoints")
    public List<Checkpoint> ARENA_CHECKPOINTS = Collections.emptyList();

    /* Misc */
    @CustomKey("arena_start_barrier_blocks")
    public List<Location> ARENA_START_BARRIER_BLOCKS = Collections.emptyList();

    /* Setup Status */
    @CustomKey("arena_setup_enabled")
    public boolean ARENA_SETUP_ENABLED = true;

}
