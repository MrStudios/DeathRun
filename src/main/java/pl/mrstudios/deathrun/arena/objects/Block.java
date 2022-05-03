package pl.mrstudios.deathrun.arena.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter @Setter
@AllArgsConstructor
public class Block {

    private Location location;
    private Material material;
    private byte data;

}
