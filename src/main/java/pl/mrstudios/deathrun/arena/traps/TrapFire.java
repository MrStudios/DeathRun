package pl.mrstudios.deathrun.arena.traps;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

@Getter @Setter
public class TrapFire extends Trap {

    public TrapFire() { super(Type.FIRE); }

    @Override
    public void onStart() {

        super.onStart();

        for (Location location : this.getTrapLocations())
            location.bukkit().getBlock().setType(Material.FIRE);

    }

    @Override
    public void onEnd() {

        super.onEnd();

        for (Location location : this.getTrapLocations())
            location.bukkit().getBlock().setType(Material.AIR);

    }

}
