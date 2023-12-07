package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaTrapActivateEvent;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ArenaRegistrableListener
public class ArenaButtonClickListener implements Listener {

    private final Arena arena;
    private final Plugin plugin;
    private final Server server;
    private final Configuration configuration;

    @Inject
    public ArenaButtonClickListener(Arena arena, Plugin plugin, Server server, Configuration configuration) {
        this.arena = arena;
        this.plugin = plugin;
        this.server = server;
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
                    ArmorStand armorStand = event.getPlayer().getWorld().spawn(trap.getButton().clone().toCenterLocation().add(0, -1, 0), ArmorStand.class, (entity) -> {

                        entity.setGravity(false);
                        entity.setInvisible(true);
                        entity.setInvulnerable(true);
                        entity.setCustomNameVisible(true);
                        entity.setCustomName(
                                this.miniMessageToLegacy(
                                        this.configuration.language().arenaHologramTrapDelayed
                                                .replace("<delay>", String.valueOf(this.configuration.plugin().arenaTrapDelay))
                                )
                        );

                    });

                    this.server.getScheduler().scheduleSyncDelayedTask(this.plugin, trap::end, trap.getDuration().toMillis() / 50L);
                    this.server.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {

                        if (System.currentTimeMillis() < this.delays.getOrDefault(trap, 0L))
                            armorStand.setCustomName(
                                    this.miniMessageToLegacy(
                                            this.configuration.language().arenaHologramTrapDelayed
                                                    .replace("<delay>", String.valueOf(Duration.ofMillis(this.delays.getOrDefault(trap, 0L) - System.currentTimeMillis()).toSeconds()))
                                    )
                            );

                        if (System.currentTimeMillis() <= this.delays.getOrDefault(trap, 0L))
                            return;

                        armorStand.remove();
                        this.delays.remove(trap);

                    }, 0, 20L);

                    this.delays.put(trap, System.currentTimeMillis() + Duration.ofSeconds(this.configuration.plugin().arenaTrapDelay).toMillis());

                });

    }

    private final Map<ITrap, Long> delays = new HashMap<>();
    protected final Collection<Material> materials = List.of(
            Material.STONE_BUTTON,
            Material.OAK_BUTTON,
            Material.ACACIA_BUTTON,
            Material.BIRCH_BUTTON,
            Material.CRIMSON_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.WARPED_BUTTON
    );

    protected String miniMessageToLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("<red>", "&c")
                .replace("<green>", "&a")
                .replace("<yellow>", "&e")
                .replace("<blue>", "&9")
                .replace("<white>", "&f")
                .replace("<black>", "&0")
                .replace("<gray>", "&7")
                .replace("<dark_gray>", "&8")
                .replace("<gold>", "&6")
                .replace("<dark_red>", "&4")
                .replace("<dark_green>", "&2")
                .replace("<dark_blue>", "&1")
                .replace("<dark_aqua>", "&3")
                .replace("<dark_purple>", "&5")
                .replace("<aqua>", "&b")
                .replace("<light_purple>", "&d")
                .replace("<bold>", "&l")
                .replace("<italic>", "&o")
                .replace("<strikethrough>", "&m")
                .replace("<underline>", "&n")
                .replace("<reset>", "&r")
                .replace("<magic>", "&k")
                .replace("<b>", "&b"));
    }

}
