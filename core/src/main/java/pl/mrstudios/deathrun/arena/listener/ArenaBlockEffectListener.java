package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

@ArenaRegistrableListener
public class ArenaBlockEffectListener implements Listener {

    private final Configuration configuration;

    @Inject
    public ArenaBlockEffectListener(Configuration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpeedEffectBlock(PlayerMoveEvent event) {

        if (event.getTo().clone().add(0, -1, 0).getBlock().getType() != this.configuration.plugin().effectsSpeedBlock)
            return;

        event.getPlayer().addPotionEffect(
                new PotionEffect(PotionEffectType.SPEED, (int) (20 * this.configuration.plugin().effectsSpeedDuration), this.configuration.plugin().effectsSpeedAmplifier, false, false, true)
        );

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJumpBoostEffectBlock(PlayerMoveEvent event) {

        if (event.getTo().clone().add(0, -1, 0).getBlock().getType() != this.configuration.plugin().effectsJumpBoostBlock)
            return;

        event.getPlayer().addPotionEffect(
                new PotionEffect(PotionEffectType.JUMP, (int) (20 * this.configuration.plugin().effectsJumpBoostDuration), this.configuration.plugin().effectsJumpBoostAmplifier, false, false, true)
        );

    }

}
