package pl.mrstudios.deathrun.arena.trap;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;

import java.util.List;

@Getter @Setter
public abstract class Trap implements ITrap {
    private Location button;
    private List<Location> locations;
}
