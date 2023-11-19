package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaTrapActivateEvent;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ArenaRegistrableListener
public class ArenaButtonClickListener implements Listener {

    private final IArena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final Configuration configuration;

    @Inject
    public ArenaButtonClickListener(IArena arena, Server server, MiniMessage miniMessage, Configuration configuration) {
        this.arena = arena;
        this.server = server;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @EventHandler
    public void onArenaButtonClick(PlayerInteractEvent event) {

        IUser user = this.arena.getUser(event.getPlayer());

        if (user == null)
            return;

        if (user.getRole() != Role.DEATH)
            return;

        if (event.getClickedBlock() == null)
            return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (this.materials.stream().noneMatch((material) -> material == event.getClickedBlock().getType()))
            return;

        this.configuration.map().arenaTraps.stream()
                .filter(
                        (trap) -> trap.getButton().getBlockX() == event.getClickedBlock().getX() && trap.getButton().getBlockY() == event.getClickedBlock().getY() && trap.getButton().getBlockZ() == event.getClickedBlock().getZ()
                )
                .findFirst()
                .ifPresent((trap) -> {

                    if (this.delays.getOrDefault(trap, 0L) > System.currentTimeMillis()) {
                        event.getPlayer().playSound(event.getPlayer().getLocation(), this.configuration.plugin().arenaSoundTrapDelay, 1.0f, 1.0f);
                        return;
                    }

                    ArenaTrapActivateEvent arenaTrapActivateEvent = new ArenaTrapActivateEvent(this.arena, trap, false);

                    this.server.getPluginManager().callEvent(arenaTrapActivateEvent);
                    if (arenaTrapActivateEvent.isCancelled())
                        return;

                    trap.start();

                    ScheduledExecutorService main = Executors.newSingleThreadScheduledExecutor(),
                            delay = Executors.newSingleThreadScheduledExecutor();

                    ArmorStand armorStand = event.getPlayer().getWorld().spawn(trap.getButton().clone().add(0, 1, 0), ArmorStand.class, (entity) -> {

                        entity.setGravity(false);
                        entity.setInvisible(true);
                        entity.setInvulnerable(true);
                        entity.setCustomNameVisible(true);
                        entity.customName(this.miniMessage.parse(
                                this.configuration.language().arenaHologramTrapDelayed
                                        .replace("<delay>", String.valueOf(this.configuration.plugin().arenaTrapDelay)))
                        );

                    });

                    main.schedule(trap::end, trap.getDuration().toMillis(), TimeUnit.MILLISECONDS);
                    delay.scheduleAtFixedRate(() -> {

                        armorStand.customName(this.miniMessage.parse(
                                this.configuration.language().arenaHologramTrapDelayed
                                        .replace("<delay>", String.valueOf(Duration.ofMillis(this.delays.getOrDefault(trap, 0L) - System.currentTimeMillis()).toSeconds()))
                        ));

                        if (System.currentTimeMillis() > this.delays.getOrDefault(trap, 0L)) {
                            this.delays.remove(trap);
                            armorStand.remove();
                            delay.shutdown();
                        }

                    }, 0, 1000, TimeUnit.MILLISECONDS);
                    this.delays.put(trap, System.currentTimeMillis() + Duration.ofSeconds(this.configuration.plugin().arenaTrapDelay).toMillis());


                });

    }

    private final Map<ITrap, Long> delays = new HashMap<>();
    private final Collection<Material> materials = List.of(
            Material.STONE_BUTTON,
            Material.OAK_BUTTON,
            Material.ACACIA_BUTTON,
            Material.BIRCH_BUTTON,
            Material.CRIMSON_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.WARPED_BUTTON
    );

}
