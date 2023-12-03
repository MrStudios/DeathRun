package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaUserLeftEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.Objects;

@ArenaRegistrableListener
public class ArenaPlayerQuitListener implements Listener {

    private final Arena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerQuitListener(Arena arena, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
        this.arena = arena;
        this.server = server;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @Deprecated
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        event.setQuitMessage("");
        if (this.arena.getUser(event.getPlayer()) == null)
            return;

        IUser user = this.arena.getUser(event.getPlayer());

        this.arena.getUsers().remove(user);
        this.arena.getUsers().forEach((target) ->
                this.audiences.player(Objects.requireNonNull(target.asBukkit())).sendMessage(this.miniMessage.deserialize(
                        this.configuration.language().chatMessageArenaPlayerLeft
                                .replace("<player>", event.getPlayer().getName())
                                .replace("<currentPlayers>", String.valueOf(this.arena.getUsers().size()))
                                .replace("<maxPlayers>", String.valueOf(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size()))
                )));

        this.arena.getSidebar().removeViewer(event.getPlayer());
        this.server.getPluginManager().callEvent(new ArenaUserLeftEvent(this.arena, user));

    }

}
