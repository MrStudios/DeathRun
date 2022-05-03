package pl.mrstudios.deathrun.arena.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter @Setter
@AllArgsConstructor
public class Location {

    private String world;

    private double x, y, z;
    private float pitch, yaw;

    public org.bukkit.Location bukkit() {
        return new org.bukkit.Location(Bukkit.getWorld(world), x + 0.5, y, z + 0.5, yaw, pitch);
    }

}
