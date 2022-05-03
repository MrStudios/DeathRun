package pl.mrstudios.deathrun.arena.traps;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

public class TrapTNT extends Trap {

    public TrapTNT() {
        super(Type.TNT);
    }

    @Override
    public void onStart() {

        for (Location location : this.getTrapLocations())
            if (location.bukkit().getBlock().getType().equals(Material.TNT)) {

                TNTPrimed tntPrimed = (TNTPrimed) location.bukkit().getWorld().spawnEntity(location.bukkit(), EntityType.PRIMED_TNT);
                tntPrimed.setFuseTicks(5);

            }

        for (Player player : this.getButtonLocation().bukkit().getWorld().getEntitiesByClass(Player.class)) {

            for (Location location : this.getTrapLocations()) {

                if (Main.instance().getArena().getUser(player).getRole().equals(Role.RUNNER)) {

                    if (player.getLocation().distanceSquared(location.bukkit()) <= 3)
                        Main.instance().getArena().getUser(player).respawn();
                    else
                        continue;

                }

                break;

            }

        }

    }

    @Override
    public void onEnd() {}

}
