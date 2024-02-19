package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaDeathEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.config.Configuration;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.util.Optional.ofNullable;
import static net.kyori.adventure.title.Title.Times.of;
import static net.kyori.adventure.title.Title.title;
import static org.bukkit.Material.LAVA;
import static org.bukkit.Material.WATER;
import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;
import static org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.PLAYING;
import static pl.mrstudios.deathrun.api.arena.user.enums.Role.RUNNER;

public class ArenaPlayerDamageListener implements Listener {

    private final Arena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerDamageListener(
            @NotNull Arena arena,
            @NotNull Server server,
            @NotNull MiniMessage miniMessage,
            @NotNull BukkitAudiences audiences,
            @NotNull Configuration configuration
    ) {
        this.arena = arena;
        this.server = server;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @EventHandler(priority = MONITOR)
    public void onDamage(@NotNull EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player player))
            return;

        if (event.getCause() == FIRE || event.getCause() == FIRE_TICK)
            event.setCancelled(true);

        if (event.getCause() == FALL && player.getFallDistance() <= this.configuration.plugin().arenaMaxFallDistance)
            event.setCancelled(true);

        if (event.getCause() == ENTITY_ATTACK || event.getCause() == ENTITY_SWEEP_ATTACK)
            event.setCancelled(true);

        if (this.arena.getGameState() != PLAYING)
            event.setCancelled(true);

        if (event.isCancelled())
            return;

        ofNullable(this.arena.getUser(player))
                .filter((user) -> user.getRole() == RUNNER)
                .ifPresent((user) -> this.callPlayerDeath(user, player));
        event.setCancelled(true);

    }

    @EventHandler(priority = MONITOR)
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {

        if (
                event.getFrom().getBlockX() == event.getTo().getBlockX()
                        && event.getFrom().getBlockY() == event.getTo().getBlockY()
                        && event.getFrom().getBlockZ() == event.getTo().getBlockZ()
                        && event.getFrom().getPitch() != event.getTo().getPitch()
                        && event.getFrom().getYaw() != event.getTo().getYaw()
        ) return;

        if (this.arena.getGameState() != PLAYING)
            return;

        if (event.getTo().getBlock().getType() != WATER && event.getTo().getBlock().getType() != LAVA)
            return;

        ofNullable(this.arena.getUser(event.getPlayer()))
                .filter((user) -> user.getRole() == RUNNER)
                .ifPresent((user) -> this.callPlayerDeath(user, event.getPlayer()));

    }

    @EventHandler(priority = MONITOR)
    public void onEntityExplode(@NotNull EntityExplodeEvent event) {

        if (this.arena.getGameState() != PLAYING)
            return;

        event.getLocation().getNearbyEntitiesByType(Player.class, 3f)
                .forEach((player) -> player.damage(1));

    }

    @EventHandler(priority = MONITOR)
    public void onCombust(@NotNull EntityCombustEvent event) {
        event.setCancelled(true);
    }

    protected void callPlayerDeath(
            @NotNull IUser user,
            @NotNull Player player
    ) {

        user.setDeaths(user.getDeaths() + 1);
        player.teleport(user.getCheckpoint().spawn());
        player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundPlayerDeath, 1.0f, 1.0f);
        player.addPotionEffect(FIRE_RESISTANCE_EFFECT);
        player.setFireTicks(0);

        this.server.getPluginManager().callEvent(new UserArenaDeathEvent(user, this.arena));
        this.audiences.player(player).showTitle(
                title(
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathTitle),
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathSubtitle),
                        of(ofMillis(250), ofSeconds(2), ofMillis(250))
                )
        );

    }

    protected static final PotionEffect FIRE_RESISTANCE_EFFECT = new PotionEffect(FIRE_RESISTANCE, 20, 1, false, false, false);

}
