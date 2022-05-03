package pl.mrstudios.deathrun.arena.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.utils.ItemUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class Strafe {

    private Type type;

    @Getter @Setter
    private static HashMap<Strafe.Type, Strafe> strafes = new HashMap<>();

    @Getter @Setter
    private static List<Cooldown> cooldowns = new ArrayList<>();

    public void boost(User user) {

        for (Cooldown cooldown : cooldowns)
            if (cooldown.getUser().getPlayer().getName().equals(user.getPlayer().getName()))
                if (this.getType().equals(cooldown.getStrafe().getType()))
                    return;

        cooldowns.add(new Cooldown(this, user));
        SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaPlayerStrafeUse());

    }

    @Getter @Setter
    public static class Cooldown extends BukkitRunnable {

        private int time;

        private User user;
        private Strafe strafe;

        public Cooldown(Strafe strafe, User user) {

            this.user = user;
            this.strafe = strafe;
            this.time = Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaStrafeDelay();

            this.runTaskTimer(Main.instance(), 0, 20);

        }

        @Override
        public void run() {

            if (!this.user.getPlayer().isOnline()) {
                this.cancel();
                return;
            }

            if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING)) {
                this.cancel();
                return;
            }

            if (!this.user.getRole().equals(Role.RUNNER)) {
                this.cancel();
                return;
            }

            if (this.time > 1) {

                this.user.getPlayer().getInventory().setItem(this.strafe.getType().getSlotId(), new ItemUtil(Material.SKULL_ITEM, time, (byte) 3).name(this.strafe.type.usedStrafeName.replace("<strafeDelay>", String.valueOf(time))).headTexture(this.strafe.getType().getUsedStrafeTexture()).itemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                this.time--;

            } else {

                this.user.getPlayer().getInventory().setItem(this.strafe.getType().getSlotId(), new ItemUtil(Material.SKULL_ITEM, 1, (byte) 3).name(this.strafe.type.availableStrafeName).lore(this.strafe.getType().name()).headTexture(this.strafe.getType().getAvailableStrafeTexture()).itemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                this.cancel();

            }

        }

        @Override
        public synchronized void cancel() throws IllegalStateException {

            Strafe.getCooldowns().remove(this);
            super.cancel();

        }
    }

    @Getter
    @AllArgsConstructor
    public enum Type {

        LEFT(
                3,
                Main.instance().getConfiguration().getMessages().getItems().getStrafeLeftDelay(),
                Main.instance().getConfiguration().getMessages().getItems().getStrafeLeftAvailable(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQyZmRlOGI4MmU4YzFiOGMyMmIyMjY3OTk4M2ZlMzVjYjc2YTc5Nzc4NDI5YmRhZGFiYzM5N2ZkMTUwNjEifX19",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjUzNDc0MjNlZTU1ZGFhNzkyMzY2OGZjYTg1ODE5ODVmZjUzODlhNDU0MzUzMjFlZmFkNTM3YWYyM2QifX19"
        ),

        BACK(
                4,
                Main.instance().getConfiguration().getMessages().getItems().getStrafeBackDelay(),
                Main.instance().getConfiguration().getMessages().getItems().getStrafeBackAvailable(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFiNjJkYjVjMGEzZmExZWY0NDFiZjcwNDRmNTExYmU1OGJlZGY5YjY3MzE4NTNlNTBjZTkwY2Q0NGZiNjkifX19",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I4M2JiY2NmNGYwYzg2YjEyZjZmNzk5ODlkMTU5NDU0YmY5MjgxOTU1ZDdlMjQxMWNlOThjMWI4YWEzOGQ4In19fQ=="
        ),

        RIGHT(
                5,
                Main.instance().getConfiguration().getMessages().getItems().getStrafeRightDelay(),
                Main.instance().getConfiguration().getMessages().getItems().getStrafeRightAvailable(),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA2MjYyYWYxZDVmNDE0YzU5NzA1NWMyMmUzOWNjZTE0OGU1ZWRiZWM0NTU1OWEyZDZiODhjOGQ2N2I5MmVhNiJ9fX0=",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGVmMzU2YWQyYWE3YjE2NzhhZWNiODgyOTBlNWZhNWEzNDI3ZTVlNDU2ZmY0MmZiNTE1NjkwYzY3NTE3YjgifX19"
        );

        private final int slotId;
        private final String usedStrafeName;
        private final String availableStrafeName;
        private final String usedStrafeTexture;
        private final String availableStrafeTexture;

    }

}
