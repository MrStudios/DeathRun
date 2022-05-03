package pl.mrstudios.deathrun.arena.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Checkpoint {

    private int id;

    private Location spawnLocation;
    private List<Location> portalLocations;

}
