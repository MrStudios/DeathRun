package pl.mrstudios.deathrun.arena.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.bukkit.item.ItemBuilder;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.booster.IBooster;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaUseBoosterEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.ibm.icu.text.UTF16.valueOf;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;
import static org.bukkit.event.EventPriority.MONITOR;
import static org.bukkit.event.block.Action.PHYSICAL;
import static org.bukkit.inventory.ItemFlag.values;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.PLAYING;

public class ArenaBoosterListener implements Listener {

    private final Arena arena;
    private final Plugin plugin;
    private final Server server;
    private final MiniMessage miniMessage;
    private final Configuration configuration;

    protected final Map<String, Map<IBooster, Long>> delay = new HashMap<>();

    @Inject
    public ArenaBoosterListener(
            @NotNull Arena arena,
            @NotNull MiniMessage miniMessage,
            @NotNull Plugin plugin,
            @NotNull Server server,
            @NotNull Configuration configuration
    ) {
        this.arena = arena;
        this.plugin = plugin;
        this.server = server;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @EventHandler(priority = MONITOR)
    public void onItemUse(PlayerInteractEvent event) {

        if (event.getAction() == PHYSICAL)
            return;

        this.configuration.plugin().boosters
                .stream()
                .filter((booster) -> event.getPlayer().getInventory().getItem(booster.slot()) != null)
                .filter((booster) -> requireNonNull(event.getPlayer().getInventory().getItem(booster.slot())).getType() == booster.item().material())
                .filter((booster) -> booster.slot() == event.getPlayer().getInventory().getHeldItemSlot())
                .findFirst().ifPresent((booster) -> {

                    IUser user = this.arena.getUser(event.getPlayer());

                    if (user == null)
                        return;

                    if (!this.delay.containsKey(event.getPlayer().getName()))
                        this.delay.put(event.getPlayer().getName(), new HashMap<>());

                    if (!this.delay.get(event.getPlayer().getName()).containsKey(booster))
                        this.delay.get(event.getPlayer().getName()).put(booster, 0L);

                    if (this.delay.getOrDefault(event.getPlayer().getName(), new HashMap<>()).getOrDefault(booster, 0L) > currentTimeMillis())
                        return;

                    AtomicReference<Integer> taskId = new AtomicReference<>(-1);

                    this.boost(event.getPlayer(), booster);
                    this.delay.get(event.getPlayer().getName()).put(booster, currentTimeMillis() + (booster.delay() * 1000L));
                    if (booster.sound() != null)
                        event.getPlayer().playSound(event.getPlayer().getLocation(), booster.sound(), 1.0f, 1.0f);

                    this.server.getPluginManager().callEvent(new UserArenaUseBoosterEvent(user, arena, booster));

                    taskId.set(
                            this.server.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {

                                if (this.arena.getGameState() != PLAYING) {

                                    this.configuration.plugin().boosters
                                            .forEach((object) -> event.getPlayer().getInventory().setItem(object.slot(), null));

                                    if (taskId.get() != -1)
                                        this.server.getScheduler().cancelTask(taskId.get());
                                    return;

                                }

                                int boosterDelay = (int) (this.delay.get(event.getPlayer().getName()).get(booster) - currentTimeMillis()) / 1000;

                                event.getPlayer().getInventory().setItem(
                                        booster.slot(),
                                        new ItemBuilder(booster.delayItem().material(), boosterDelay)
                                                .name(this.miniMessage.deserialize(booster.delayItem().name().replace("<delay>", valueOf(boosterDelay))))
                                                .texture((booster.delayItem().texture() != null) ? requireNonNull(booster.delayItem().texture()) : "")
                                                .itemFlags(values())
                                                .build()
                                );

                                if (boosterDelay >= 1)
                                    return;

                                event.getPlayer().getInventory().setItem(
                                        booster.slot(),
                                        new ItemBuilder(booster.item().material())
                                                .name(this.miniMessage.deserialize(booster.item().name()))
                                                .texture((booster.item().texture() != null) ? requireNonNull(booster.item().texture()) : "")
                                                .itemFlags(values())
                                                .build()
                                );

                                if (taskId.get() != -1)
                                    this.server.getScheduler().cancelTask(taskId.get());

                            }, 0L, 20L)
                    );

                });

    }

    protected void boost(Player player, IBooster booster) {

        switch (booster.direction()) {

            case FORWARD ->
                player.setVelocity(player.getLocation().getDirection().multiply(booster.power()).setY(0.25));

            case BACKWARD ->
                player.setVelocity(player.getLocation().getDirection().multiply(-booster.power()).setY(0.25));

            case LEFT ->
                player.setVelocity(player.getLocation().getDirection().multiply(booster.power()).rotateAroundY(Math.toRadians(90)).setY(0.25));

            case RIGHT ->
                player.setVelocity(player.getLocation().getDirection().multiply(booster.power()).rotateAroundY(Math.toRadians(-90)).setY(0.25));

        }

    }


}
