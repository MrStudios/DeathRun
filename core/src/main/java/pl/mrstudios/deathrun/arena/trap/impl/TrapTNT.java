package pl.mrstudios.deathrun.arena.trap.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import pl.mrstudios.deathrun.arena.trap.Trap;

import java.time.Duration;

@Getter @Setter
public class TrapTNT extends Trap {

    @Override
    public void start() {

        super.getLocations().forEach((location) -> {

            if (location.getBlock().getType() == Material.TNT)
                ((TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT))
                        .setFuseTicks(5);

        });

    }

    @Override
    public void end() {}

    @Override
    public void setExtra(Object... objects) {}

    @Override
    public Duration getDuration() {
        return Duration.ZERO;
    }

}
