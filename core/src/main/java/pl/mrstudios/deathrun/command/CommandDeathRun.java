package pl.mrstudios.deathrun.command;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.SneakyThrows;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.arena.checkpoint.Checkpoint;
import pl.mrstudios.deathrun.arena.pad.TeleportPad;
import pl.mrstudios.deathrun.arena.trap.TrapRegistry;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.util.ChannelUtil;
import pl.mrstudios.deathrun.util.ZipUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Command(
        name = "deathrun",
        aliases = { "dr" }
) @SuppressWarnings("unused")
@Permission("mrstudios.command.deathrun")
public class CommandDeathRun {

    private final Plugin plugin;
    private final WorldEdit worldEdit;

    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;

    private final TrapRegistry  trapRegistry;
    private final Configuration configuration;

    @Inject
    public CommandDeathRun(Plugin plugin, WorldEdit worldEdit, BukkitAudiences audiences, MiniMessage miniMessage, TrapRegistry  trapRegistry, Configuration configuration) {
        this.plugin = plugin;
        this.worldEdit = worldEdit;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.trapRegistry = trapRegistry;
        this.configuration = configuration;
    }

    @Execute()
    public void noArguments(@Context Player player) {

        this.message(player, String.join("<br>",
                "<reset>",
                "<reset>    <gold>DeathRun <dark_gray>(v%s) <gray>by <white>MrStudios Industries",
                "<reset>",
                "<reset> <b>*</b> <white>/deathrun leave",
                "<reset> <b>*</b> <white>/deathrun setup",
                "<reset>"
        ), this.plugin.getDescription().getVersion());
    }

    @Execute(name = "leave")
    @Permission("mrstudios.command.deathrun.leave")
    public void leave(@Context Player player) {
        ChannelUtil.connect(this.plugin, player, this.configuration.plugin().server);
    }

    /* Setup Command */
    @Execute(name = "setup")
    @Permission("mrstudios.command.deathrun.setup")
    public void noArgumentsSetup(@Context Player player) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.message(player, String.join("<br>",
                "<reset>",
                "<reset>    <gold>DeathRun <dark_gray>(v%s) <gray>by <white>MrStudios Industries",
                "<reset>",
                "<reset> <b>*</b> <white>/deathrun setup setname <name>",
                "<reset> <b>*</b> <white>/deathrun setup setwaitinglobby",
                "<reset> <b>*</b> <white>/deathrun setup setstartbarrier (material)",
                "<reset> <b>*</b> <white>/deathrun setup addspawn <death/runner>",
                "<reset> <b>*</b> <white>/deathrun setup addtrap <type> (material)",
                "<reset> <b>*</b> <white>/deathrun setup addcheckpoint",
                "<reset> <b>*</b> <white>/deathrun setup addteleport",
                "<reset> <b>*</b> <white>/deathrun setup save",
                "<reset>"
        ), this.plugin.getDescription().getVersion());
    }

    @Execute(name = "setup addcheckpoint")
    @Permission("mrstudios.command.deathrun.setup")
    public void addCheckpoint(@Context Player player) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.configuration.map().arenaCheckpoints.add(
                new Checkpoint(this.configuration.map().arenaCheckpoints.size(), player.getLocation().toCenterLocation(), this.locations(player))
        );

        this.message(player, "<reset> <dark_green><b>*</b> <green>Arena checkpoint has been added.");

    }

    @Execute(name = "setup addspawn")
    @Permission("mrstudios.command.deathrun.setup")
    public void addRunnerSpawn(@Context Player player, @Arg("role") Role role) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        switch (role) {

            case RUNNER ->
                    this.configuration.map().arenaRunnerSpawnLocations.add(player.getLocation().toCenterLocation());

            case DEATH ->
                    this.configuration.map().arenaDeathSpawnLocations.add(player.getLocation().toCenterLocation());

            default ->
                    this.message(player, "<reset> <dark_red><b>*</b> <red>You must select <dark_red>RUNNER <red>or <dark_red>DEATH <red>role.");

        }

        if (role != Role.DEATH && role != Role.RUNNER)
            return;

        this.message(player, "<reset> <dark_green><b>*</b> <green>Added <dark_green>%s <green>role spawn.", role.name());

    }

    @Execute(name = "setup addtrap")
    @Permission("mrstudios.command.deathrun.setup")
    public void addTrap(@Context Player player, @Arg("type") String type) {
        this.addTrap(player, type, null);
    }
    
    @Execute(name = "setup addtrap")
    @Permission("mrstudios.command.deathrun.setup")
    public void addTrap(@Context Player player, @Arg("type") String type, @Arg("material") Material material) {
        this.trap(player, type, material);
    }

    @Execute(name = "setup setname")
    @Permission("mrstudios.command.deathrun.setup")
    public void setName(@Context Player player, @Arg("name") String name) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.configuration.map().arenaName = name;
        this.message(player, "<reset> <dark_green><b>*</b> <green>Arena name has been set to <dark_green>%s<green>.", name);

    }

    @Execute(name = "setup setstartbarrier")
    @Permission("mrstudios.command.deathrun.setup")
    public void setStartBarrier(@Context Player player) {
        this.setStartBarrier(player, null);
    }

    @Execute(name = "setup setstartbarrier")
    @Permission("mrstudios.command.deathrun.setup")
    public void setStartBarrier(@Context Player player, @Arg("material") Material material) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        List<Location> locations = this.locations(player);

        if (material != null)
            locations.removeIf((location) -> !location.getBlock().getType().equals(material));

        this.configuration.map().arenaStartBarrierBlocks = locations;
        this.message(player, "<reset> <dark_green><b>*</b> <green>Arena start barrier has been set.");

    }

    @Execute(name = "setup setwaitinglobby")
    @Permission("mrstudios.command.deathrun.setup")
    public void setWaitingLobby(@Context Player player) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.configuration.map().arenaWaitingLobbyLocation = player.getLocation().toCenterLocation();
        this.message(player, "<reset> <dark_green><b>*</b> <green>Arena waiting lobby has been set.");

    }

    @Execute(name = "setup addteleport")
    @Permission("mrstudios.command.deathrun.setup")
    public void addTeleportPad(@Context Player player) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.configuration.map().teleportPads.add(new TeleportPad(locations(player).get(0), player.getLocation().toCenterLocation().add(0, -0.5, 0)));
        this.message(player, "<reset> <dark_green><b>*</b> <green>Added arena teleport pad.");

    }

    @Execute(name = "setup save")
    @Permission("mrstudios.command.deathrun.setup")
    public void save(@Context Player player) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        this.configuration.map().arenaSetupEnabled = false;
        this.configuration.map().save();

        ZipUtil.zip(new File(this.plugin.getDataFolder(), "backup/" + player.getWorld().getName() + ".zip"), new Path[] {
                player.getWorld().getWorldFolder().toPath()
        });

        this.message(player, "<reset> <dark_green><b>*</b> <green>Arena configuration saved successfully, please restart server to apply changes.");

    }

    protected void message(Player player, String message, Object... args) {
        this.audiences.player(player).sendMessage(this.miniMessage.deserialize(String.format(message, args)));
    }

    protected List<Location> locations(Player player) {

        try {

            List<Location> locations = new ArrayList<>();

            LocalSession session = this.worldEdit.getSessionManager().findByName(player.getName());

            assert session != null;
            Region region = session.getSelection(session.getSelectionWorld());

            region.forEach((vector) -> locations.add(BukkitAdapter.adapt(player.getWorld(), vector)));

            return locations;

        } catch (Exception ignored) {}

        return Collections.emptyList();

    }

    @SneakyThrows
    protected void trap(Player player, String type, Object... objects) {

        if (!this.configuration.map().arenaSetupEnabled) {
            this.audiences.player(player).sendMessage(this.miniMessage.deserialize("<reset> <dark_red><b>*</b> <red>You can't use that command while setup is disabled."));
            return;
        }

        Block target = player.getTargetBlock(null, 250);
        List<Location> locations = this.locations(player);
        Class<? extends ITrap> trapClass = this.trapRegistry.get(type.toUpperCase());

        if (Stream.of(
                Material.STONE_BUTTON,
                Material.OAK_BUTTON,
                Material.ACACIA_BUTTON,
                Material.BIRCH_BUTTON,
                Material.CRIMSON_BUTTON,
                Material.JUNGLE_BUTTON,
                Material.SPRUCE_BUTTON,
                Material.WARPED_BUTTON
        ).noneMatch((button) -> target.getType().equals(button))) {
            this.message(player, "<reset> <dark_red><b>*</b> <red>You must look at button that is activating trap.");
            return;
        }

        if (trapClass == null) {
            this.message(player, "<reset> <dark_red><b>*</b> <red>Trap <dark_red>%s <red>is not exists.", type.toUpperCase());
            return;
        }

        ITrap trap = trapClass.getDeclaredConstructor().newInstance();

        trap.setButton(target.getLocation());
        trap.setLocations(trap.filter(locations, objects));

        if (objects != null)
            trap.setExtra(objects);

        this.configuration.map().arenaTraps.add(trap);
        this.message(player, "<reset> <dark_green><b>*</b> <green>Added trap <dark_green>%s <green>to arena.", type.toUpperCase());

    }

}
