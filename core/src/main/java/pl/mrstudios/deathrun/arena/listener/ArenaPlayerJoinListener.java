package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.bukkit.item.ItemBuilder;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaUserJoinedEvent;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.user.User;
import pl.mrstudios.deathrun.config.Configuration;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Objects.requireNonNull;
import static org.bukkit.GameMode.ADVENTURE;
import static org.bukkit.Material.RED_BED;
import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.potion.PotionEffectType.NIGHT_VISION;
import static org.bukkit.potion.PotionEffectType.SATURATION;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.STARTING;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.WAITING;

public class ArenaPlayerJoinListener implements Listener {

    private final Arena arena;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public ArenaPlayerJoinListener(
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

    @SuppressWarnings("deprecation")
    @EventHandler(priority = MONITOR)
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {

        event.setJoinMessage("");
        if (this.arena.getGameState() != WAITING && this.arena.getGameState() != STARTING)
            return;

        if (this.arena.getUser(event.getPlayer()) != null)
            return;

        User user = new User(event.getPlayer());

        this.arena.getUsers().add(user);
        this.arena.getUsers().forEach((target) ->
                this.audiences.player(requireNonNull(target.asBukkit())).sendMessage(this.miniMessage.deserialize(
                        this.configuration.language().chatMessageArenaPlayerJoined
                                .replace("<player>", event.getPlayer().getDisplayName())
                                .replace("<currentPlayers>", String.valueOf(this.arena.getUsers().size()))
                                .replace("<maxPlayers>", String.valueOf(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size()))
                )));

        event.getPlayer().getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(event.getPlayer()::removePotionEffect);

        event.getPlayer().getInventory().clear();
        event.getPlayer().setGameMode(ADVENTURE);
        event.getPlayer().teleport(this.configuration.map().arenaWaitingLobbyLocation);
        event.getPlayer().addPotionEffect(new PotionEffect(SATURATION, MAX_VALUE, 1, false, false, false));
        event.getPlayer().addPotionEffect(new PotionEffect(NIGHT_VISION, MAX_VALUE, 1, false, false, false));

        this.arena.getSidebar().addViewer(event.getPlayer());
        this.server.getPluginManager().callEvent(new ArenaUserJoinedEvent(user, this.arena));

        event.getPlayer().getInventory().setItem(
                8, new ItemBuilder(RED_BED)
                        .name(this.miniMessage.deserialize(this.configuration.language().arenaItemLeaveName))
                        .itemFlags(ItemFlag.values())
                        .build()
        );

    }

}
