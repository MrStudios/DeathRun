package pl.mrstudios.deathrun.arena.traps;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.DelayExecution;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

public class TrapGiant extends Trap {

    public TrapGiant() { super(Type.GIANT); }

    @Override
    public void onStart() {

        for (Location location : this.getTrapLocations()) {

            Giant giant = (Giant) location.bukkit().getWorld().spawnEntity(location.bukkit(), EntityType.GIANT);
            TNTPrimed tnt = (TNTPrimed) location.bukkit().getWorld().spawnEntity(location.bukkit(), EntityType.PRIMED_TNT);

            tnt.setFuseTicks(10);
            giant.setCanPickupItems(false);
            giant.setMaxHealth(10);
            giant.setHealth(10);

            for (Player player : this.getButtonLocation().bukkit().getWorld().getEntitiesByClass(Player.class))
                if (player.getLocation().distanceSquared(location.bukkit()) <= 10)
                    if (Main.instance().getArena().getUser(player).getRole().equals(Role.RUNNER))
                        Main.instance().getArena().getUser(player).respawn();

            new DelayExecution(1) {

                @Override
                public void run() {

                    giant.damage(20);

                    if (!giant.isDead())
                        giant.remove();

                }

            };

        }

    }

    @Override
    public void onEnd() {}

}
