package pl.mrstudios.deathrun.arena.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.DelayExecution;
import pl.mrstudios.deathrun.arena.objects.Trap;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.arena.tasks.TrapCooldownTask;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.util.Objects;

public class ArenaButtonClickEvent implements Listener {

    @EventHandler
    public void onPlayerButtonClick(PlayerInteractEvent event) {

        try {

            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getClickedBlock().getType().equals(Material.WOOD_BUTTON) && !event.getClickedBlock().getType().equals(Material.STONE_BUTTON))
                return;

            User user = Main.instance().getArena().getUser(event.getPlayer());
            Trap trap = Trap.createTrapInstance(Main.instance().getArena().getTrap(event.getClickedBlock().getLocation()), Main.instance().getArena().getTrap(event.getClickedBlock().getLocation()).getType().getCast());

            if (Objects.isNull(trap)) {
                event.setCancelled(true);
                return;
            }

            if (!user.getRole().equals(Role.DEATH)) {
                event.setCancelled(true);
                return;
            }

            if (trap.hasCooldown()) {
                SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaTrapDelayEvent());
                event.setCancelled(true);
                return;
            }

            TrapCooldownTask.getInstance().getCooldowns().add(new Trap.Cooldown(trap.getButtonLocation()));

            trap.onStart();

            new DelayExecution(4) {

                @Override
                public void run() {
                    trap.onEnd();
                }

            };

        } catch (Exception ignored) {}

    }

}
