package pl.mrstudios.deathrun.arena.strafes;

import org.bukkit.util.Vector;
import pl.mrstudios.deathrun.arena.objects.Strafe;
import pl.mrstudios.deathrun.arena.objects.User;

public class StrafeBack extends Strafe {

    public StrafeBack() {
        super(Type.BACK);
    }

    @Override
    public void boost(User user) {

        super.boost(user);

        double rotation = (user.getPlayer().getLocation().getYaw() - 45) % 360;

        if (rotation < 0)
            rotation += 360.0;

        Vector vector;

        if (rotation >= -45.5 && 90.5 > rotation)
            vector = user.getPlayer().getLocation().getDirection().setY(0.3).setX(2.5).setZ(0);
        else if (rotation >= 90.5 && 180.5 > rotation)
            vector = user.getPlayer().getLocation().getDirection().setY(0.3).setZ(2.5).setX(0);
        else if (rotation >= 180.5 && 270.5 > rotation)
            vector = user.getPlayer().getLocation().getDirection().setY(0.3).setX(-2.5).setZ(0);
        else
            vector = user.getPlayer().getLocation().getDirection().setY(0.3).setZ(-2.5).setX(0);

        user.getPlayer().setVelocity(vector);

    }

}