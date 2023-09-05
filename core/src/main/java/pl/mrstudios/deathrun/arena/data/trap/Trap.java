package pl.mrstudios.deathrun.arena.data.trap;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.interfaces.ITrap;
import pl.mrstudios.deathrun.api.data.annotations.Serializable;

import java.util.List;

@Getter @Setter
public abstract class Trap implements ITrap {

    @Serializable
    private String id;

    /* Instance Data */
    @Serializable
    private Location button;

    @Serializable
    private List<Location> locations;

    /* Constructor */
    public Trap(String id) {
        this.id = id;
    }

}
