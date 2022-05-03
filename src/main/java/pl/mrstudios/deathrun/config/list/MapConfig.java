package pl.mrstudios.deathrun.config.list;

import lombok.Getter;
import lombok.Setter;
import pl.mrstudios.deathrun.arena.objects.Checkpoint;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

import java.util.List;

@Getter @Setter
public class MapConfig {

    private String name;

    private Location waitingLobby;

    private List<Location> deathSpawns;
    private List<Location> runnerSpawns;

    private List<Location> startGlass;

    private List<Trap> traps;
    private List<Checkpoint> checkpoints;

    private boolean setup;

}
