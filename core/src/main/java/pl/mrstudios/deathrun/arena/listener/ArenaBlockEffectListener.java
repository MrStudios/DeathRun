package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
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
    public void onStepOnBlockEffect(PlayerMoveEvent event) {

        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ() && event.getFrom().getPitch() != event.getTo().getPitch() && event.getFrom().getYaw() != event.getTo().getYaw())
            return;

        this.configuration.plugin().blockEffects
                .stream()
                .filter((effect) -> effect.blockType() == event.getTo().clone().add(0, -1, 0).getBlock().getType())
                .findFirst()
                .ifPresent((effect) -> event.getPlayer().addPotionEffect(new PotionEffect(effect.effectType(), (int) (20 * effect.duration()), effect.amplifier(), false, false, true)));

    }

}
