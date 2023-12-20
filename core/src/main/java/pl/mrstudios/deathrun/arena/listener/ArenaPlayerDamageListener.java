package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaDeathEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.time.Duration;

@ArenaRegistrableListener
public class ArenaPlayerDamageListener implements Listener {

    private final Arena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerDamageListener(Arena arena, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
        this.arena = arena;
        this.server = server;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player player))
            return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && player.getFallDistance() <= this.configuration.plugin().arenaMaxFallDistance)
            event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
            event.setCancelled(true);

        if (event.isCancelled())
            return;

        IUser user = this.arena.getUser(player);
        if (user == null)
            return;

        if (user.getRole() != Role.RUNNER)
            event.setCancelled(true);

        if (this.arena.getGameState() != GameState.PLAYING)
            event.setCancelled(true);

        if (event.isCancelled())
            return;

        this.playerDeath(user, player);
        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ() && event.getFrom().getPitch() != event.getTo().getPitch() && event.getFrom().getYaw() != event.getTo().getYaw())
            return;

        if (event.getTo().getBlock().getType() != Material.WATER && event.getTo().getBlock().getType() != Material.LAVA)
            return;

        IUser user = this.arena.getUser(event.getPlayer());
        if (user == null)
            return;

        if (user.getRole() != Role.RUNNER)
            return;

        if (this.arena.getGameState() != GameState.PLAYING)
            return;

        this.playerDeath(user, event.getPlayer());

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        if (this.arena.getGameState() != GameState.PLAYING)
            return;

        event.getLocation().getNearbyEntitiesByType(Player.class, 3f)
                .forEach((player) -> player.damage(1));

    }

    @EventHandler
    public void onFire(EntityCombustEvent event) {
        event.setCancelled(true);
    }

    protected void playerDeath(IUser user, Player player) {

        user.setDeaths(user.getDeaths() + 1);
        player.teleport(user.getCheckpoint().spawn());
        player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundPlayerDeath, 1.0f, 1.0f);

        this.server.getPluginManager().callEvent(new UserArenaDeathEvent(user, this.arena));
        this.audiences.player(player).showTitle(
                Title.title(
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathTitle),
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathSubtitle),
                        Title.Times.of(Duration.ofMillis(250), Duration.ofSeconds(2), Duration.ofMillis(250))
                )
        );

    }

}
