package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaUserJoinedEvent;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.arena.user.User;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.Objects;

@ArenaRegistrableListener
public class ArenaPlayerJoinListener implements Listener {

    private final IArena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerJoinListener(IArena arena, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
        this.arena = arena;
        this.server = server;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @Deprecated
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage("");
        if (this.arena.getUser(event.getPlayer()) != null)
            return;

        User user = new User(event.getPlayer());

        this.arena.getUsers().add(user);
        this.arena.getUsers().forEach((target) ->
                this.audiences.player(Objects.requireNonNull(target.asBukkit())).sendMessage(this.miniMessage.deserialize(
                        this.configuration.language().chatMessageArenaPlayerJoined
                                .replace("<player>", event.getPlayer().getName())
                                .replace("<currentPlayers>", String.valueOf(this.arena.getUsers().size()))
                                .replace("<maxPlayers>", String.valueOf(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size()))
                )));

        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().teleport(this.configuration.map().arenaWaitingLobbyLocation);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1, false, false));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false));

        this.arena.getSidebar().addViewer(event.getPlayer());
        this.server.getPluginManager().callEvent(new ArenaUserJoinedEvent(this.arena, user));

    }

}
