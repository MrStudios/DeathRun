package pl.mrstudios.deathrun.arena.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.tasks.TrapCooldownTask;
import pl.mrstudios.deathrun.arena.traps.*;
import pl.mrstudios.deathrun.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Trap {

    private int id;
    private final Type type;

    private Location buttonLocation;
    private List<Location> trapLocations;

    public void onStart() {}
    public void onEnd() {}

    public boolean hasCooldown() {

        for (Cooldown cooldown : TrapCooldownTask.getInstance().getCooldowns())
            if (cooldown.getLocation().equals(this.buttonLocation))
                return true;

        return false;

    }

    public static <T> T createTrapInstance(Trap trap, Class<T> typeOfClass) {

        try {

            Trap castedObject = trap.getType().getCast().newInstance();

            castedObject.setId(trap.getId());
            castedObject.setButtonLocation(trap.getButtonLocation());
            castedObject.setTrapLocations(trap.getTrapLocations());

            return typeOfClass.cast(castedObject);

        } catch (Exception ignored) {}

        return null;

    }

    @Getter
    @AllArgsConstructor
    public enum Type {

        TNT(TrapTNT.class),
        BLOCKS(TrapBlocks.class),
        WALL(TrapWall.class),
        GIANT(TrapGiant.class),
        WATER(TrapWater.class),
        LAVA(TrapLava.class),
        FIRE(TrapFire.class),
        ARROWS(TrapArrows.class),
        PISTONS(TrapPistons.class);


        private final Class<? extends Trap> cast;

    }

    @Getter @Setter
    public static class Cooldown {

        private int time;
        private Location location;
        private ArmorStand armorStand;

        public Cooldown(Location buttonLocation) {

            this.location = buttonLocation;
            this.time = Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaTrapDelay();
            this.armorStand = (ArmorStand) this.location.bukkit().getWorld().spawnEntity(this.location.bukkit(), EntityType.ARMOR_STAND);

            this.armorStand.setVisible(false);
            this.armorStand.setGravity(false);
            this.armorStand.setMarker(true);
            this.armorStand.setCustomName(new MessageUtil(Main.instance().getConfiguration().getMessages().getHolograms().getTrapDelayHologram().replace("<trapDelayTimer>", String.valueOf(time))).color().getMessage());
            this.armorStand.setCustomNameVisible(true);

            for (Location location : this.getNearBlocks())
                location.bukkit().getBlock().setTypeIdAndData(location.bukkit().getBlock().getType().getId(), (byte) 14, false);

        }

        public void removeTimeUnit() {

            if (time > 0) {
                this.armorStand.setCustomName(new MessageUtil(Main.instance().getConfiguration().getMessages().getHolograms().getTrapDelayHologram().replace("<trapDelayTimer>", String.valueOf(time))).color().getMessage());
                this.time--;
            } else {
                this.armorStand.remove();
                for (Location location : this.getNearBlocks())
                    location.bukkit().getBlock().setTypeIdAndData(location.bukkit().getBlock().getType().getId(), (byte) 5, false);
                TrapCooldownTask.getInstance().getCooldowns().remove(this);
            }

        }

        public List<Location> getNearBlocks() {

            List<Location> locations = new ArrayList<>();

            int minX = (int) (this.location.getX() - 2),
                    minY = (int) (this.location.getY() - 2),
                    minZ = (int) (this.location.getZ() - 2);

            int maxX = (int) (this.location.getX() + 2),
                    maxY = (int) (this.location.getY() + 2),
                    maxZ = (int) (this.location.getZ() + 2);

            for (int x = minX; x <= maxX; x++)
                for (int y = minY; y <= maxY; y++)
                    for (int z = minZ; z <= maxZ; z++) {

                        Location location = new Location(this.location.getWorld(), x, y, z, 0, 0);

                        if (location.bukkit().getBlock().getTypeId() == Material.STAINED_CLAY.getId())
                            locations.add(location);

                        if (location.bukkit().getBlock().getTypeId() == Material.STAINED_GLASS.getId())
                            locations.add(location);

                    }

            return locations;

        }

    }

}
