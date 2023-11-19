package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaCheckpointEvent;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaFinishedEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.time.Duration;
import java.util.Objects;

@ArenaRegistrableListener
public class ArenaCheckpointReachedEvent implements Listener {

    private final IArena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaCheckpointReachedEvent(IArena arena, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
        this.arena = arena;
        this.server = server;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (event.getTo().getBlock().getType() != Material.NETHER_PORTAL)
            return;

        this.configuration.map().arenaCheckpoints.stream()
                .filter((checkpoint) -> checkpoint.locations().stream().anyMatch(
                        (location) -> location.getBlockX() == event.getTo().getBlockX() && location.getBlockY() == event.getTo().getBlockY() && location.getBlockZ() == event.getTo().getBlockZ()
                ))
                .findFirst()
                .ifPresent((checkpoint) -> {

                    IUser user = this.arena.getUser(event.getPlayer());
                    if (user == null)
                        return;

                    if (user.getCheckpoint().id() >= checkpoint.id())
                        return;

                    UserArenaCheckpointEvent userArenaCheckpointEvent = new UserArenaCheckpointEvent(user, checkpoint);

                    this.server.getPluginManager().callEvent(userArenaCheckpointEvent);
                    if (event.isCancelled())
                        return;

                    user.setCheckpoint(checkpoint);
                    this.audiences.player(event.getPlayer()).showTitle(
                            Title.title(
                                    this.miniMessage.parse(this.configuration.language().arenaCheckpointTitle),
                                    this.miniMessage.parse(this.configuration.language().arenaCheckpointSubtitle),
                                    Title.Times.of(Duration.ofMillis(250), Duration.ofSeconds(3), Duration.ofMillis(250))
                            )
                    );

                    if (checkpoint.id() != this.configuration.map().arenaCheckpoints.get(this.configuration.map().arenaCheckpoints.size() - 1).id())
                        return;

                    this.arena.setFinishedRuns(this.arena.getFinishedRuns() + 1);

                    int position = this.arena.getFinishedRuns(), time = (int) Duration.ofMillis(System.currentTimeMillis() - this.arena.getStartTime()).toSeconds();
                    this.server.getPluginManager().callEvent(
                            new UserArenaFinishedEvent(user, time, position)
                    );

                    this.audiences.player(event.getPlayer()).showTitle(
                            Title.title(
                                    this.miniMessage.parse(this.configuration.language().arenaFinishTitle),
                                    this.miniMessage.parse(this.configuration.language().arenaFinishSubtitle),
                                    Title.Times.of(Duration.ofMillis(250), Duration.ofSeconds(3), Duration.ofMillis(250))
                            )
                    );

                    user.setRole(Role.SPECTATOR);
                    event.getPlayer().teleport(this.configuration.map().arenaCheckpoints.get(0).spawn());
                    this.arena.getUsers().stream()
                            .map(IUser::asBukkit)
                            .filter(Objects::nonNull)
                            .forEach((target) -> this.audiences.player(target).sendMessage(this.miniMessage.parse(
                                    this.configuration.language().chatMessageArenaPlayerFinished
                                            .replace("<player>", event.getPlayer().getName())
                                            .replace("<time>", String.valueOf(time))
                                            .replace("<finishPosition>", String.valueOf(position))
                            )));

                });

    }

}
