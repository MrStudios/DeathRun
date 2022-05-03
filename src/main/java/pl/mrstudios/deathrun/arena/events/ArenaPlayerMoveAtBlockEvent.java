package pl.mrstudios.deathrun.arena.events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.User;

public class ArenaPlayerMoveAtBlockEvent implements Listener {

    @EventHandler
    public void onPlayerMoveAtBlock(PlayerMoveEvent event) {

        User user = Main.instance().getArena().getUser(event.getPlayer());
        Block blockUnder = event.getPlayer().getLocation().getWorld().getBlockAt(new org.bukkit.Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() - 1, event.getPlayer().getLocation().getBlockZ(), 0, 0)),
                blockCurrent = event.getPlayer().getLocation().getWorld().getBlockAt(new org.bukkit.Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY(), event.getPlayer().getLocation().getBlockZ(), 0, 0)),
                blockUp = event.getPlayer().getLocation().getWorld().getBlockAt(new org.bukkit.Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() + 1, event.getPlayer().getLocation().getBlockZ(), 0, 0));

        if (!user.getRole().equals(Role.RUNNER))
            return;

        switch (blockUnder.getType()) {

            case EMERALD_BLOCK: {
                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int) (20 * Main.instance().getConfiguration().getSettings().getArena().getEffects().getJumpBoostTime()), Main.instance().getConfiguration().getSettings().getArena().getEffects().getJumpBoostAmplifier(), false));
                break;
            }

            case REDSTONE_BLOCK: {
                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) (20 * Main.instance().getConfiguration().getSettings().getArena().getEffects().getSpeedTime()), Main.instance().getConfiguration().getSettings().getArena().getEffects().getSpeedAmplifier(), false));
                break;
            }

            default:
                break;

        }

        switch (blockCurrent.getType()) {

            case WATER:
            case LAVA:
            case STATIONARY_WATER:
            case STATIONARY_LAVA: {

                user.respawn();
                break;

            }

            default:
                break;

        }

        switch (blockUp.getType()) {

            case WATER:
            case LAVA:
            case STATIONARY_WATER:
            case STATIONARY_LAVA: {

                user.respawn();
                break;

            }

            default:
                break;

        }

    }

}
