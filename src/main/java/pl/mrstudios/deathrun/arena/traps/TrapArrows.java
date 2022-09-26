package pl.mrstudios.deathrun.arena.traps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import lombok.Getter;
import lombok.Setter;
import pl.mrstudios.deathrun.arena.objects.Trap;

@Getter @Setter
public class TrapArrows extends Trap implements ProjectileSource {

    protected final BlockFace[] faces = new BlockFace[] {
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.UP,
        BlockFace.DOWN
    };


    public TrapArrows() { super(Type.ARROWS); }

    @Override
    public void onStart() {
        for (pl.mrstudios.deathrun.arena.objects.Location loc : this.getTrapLocations())
            shoot(loc.bukkit());
    }

    protected void shoot(final Location origin) {
        final Block originBlock = origin.getBlock();

        for (BlockFace face : faces) {
            if (originBlock.getRelative(face).getType().isSolid()) {
                continue;
            }

            final Location source = originBlock.getRelative(face).getLocation();
            final Location target = originBlock.getRelative(face).getRelative(face).getLocation();

            final Vector direction = target.toVector().subtract(source.toVector());

            final Arrow arrow = (Arrow) origin.getWorld().spawnArrow(source, direction, 1.6f, 12);

            arrow.setShooter(this);
        }
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return null;
    }


    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        return null;
    }
}
