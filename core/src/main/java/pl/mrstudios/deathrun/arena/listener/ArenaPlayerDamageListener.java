package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaDeathEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.time.Duration;

@ArenaRegistrableListener
public class ArenaPlayerDamageListener implements Listener {

    private final IArena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerDamageListener(IArena arena, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
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

        IUser user = this.arena.getUser(player);
        if (user == null)
            return;

        if (user.getRole() != Role.RUNNER)
            event.setCancelled(true);

        if (this.arena.getGameState() != GameState.PLAYING)
            event.setCancelled(true);

        if (event.isCancelled())
            return;

        user.setDeaths(user.getDeaths() + 1);
        player.teleport(user.getCheckpoint().spawn());
        this.server.getPluginManager().callEvent(new UserArenaDeathEvent(user));
        this.audiences.player(player).showTitle(
                Title.title(
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathTitle),
                        this.miniMessage.deserialize(this.configuration.language().arenaDeathSubtitle),
                        Title.Times.of(Duration.ofMillis(250), Duration.ofSeconds(3), Duration.ofMillis(250))
                )
        );

        event.setCancelled(true);

    }

}
