package pl.mrstudios.deathrun.arena.traps;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

public class TrapPistons extends Trap {

    public TrapPistons() {
        super(Type.PISTONS);
    }

    @Override
    public void onStart() {

        for (Player player : this.getButtonLocation().bukkit().getWorld().getEntitiesByClass(Player.class)) {

            boolean soundPlayed = false;
            for (Location location : this.getTrapLocations()) {

                if (player.getLocation().distanceSquared(location.bukkit()) <= 10 && !soundPlayed) {
                    soundPlayed = true;
                    player.playSound(location.bukkit(), Sound.PISTON_EXTEND, 1, 1);
                }

                if (Main.instance().getArena().getUser(player).getRole().equals(Role.RUNNER)) {

                    if (player.getLocation().distanceSquared(location.bukkit()) <= 1.5) {

                        try {
                            player.setVelocity(player.getLocation().getDirection().setY(5).setZ(0).setX(0));
                        } catch (Exception ignored) {}

                        break;

                    }

                }

            }

        }

    }

    @Override
    public void onEnd() {}

}
