package pl.mrstudios.deathrun;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mrstudios.deathrun.arena.Arena;
import pl.mrstudios.deathrun.arena.events.*;
import pl.mrstudios.deathrun.arena.objects.Strafe;
import pl.mrstudios.deathrun.arena.strafes.StrafeBack;
import pl.mrstudios.deathrun.arena.strafes.StrafeLeft;
import pl.mrstudios.deathrun.arena.strafes.StrafeRight;
import pl.mrstudios.deathrun.arena.tasks.ArenaMainTask;
import pl.mrstudios.deathrun.arena.tasks.TrapCooldownTask;
import pl.mrstudios.deathrun.commands.CommandDeathRun;
import pl.mrstudios.deathrun.commands.CommandLeave;
import pl.mrstudios.deathrun.commands.CommandStart;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.events.WandClickListener;
import pl.mrstudios.deathrun.hooks.PlaceholderHook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class Main extends JavaPlugin {

    @Setter
    private static Main instance;

    /* Plugin Variables */
    private Arena arena;
    private Configuration configuration;

    @Override
    public void onEnable() {

        setInstance(this);
        long currentTime = System.currentTimeMillis();

        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&m------------------------------------"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r            &bDeath Run"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r        &7Created by &3MrStudios"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));

        this.configuration = new Configuration();
        this.arena = new Arena();

        /* Command Registry */
        getCommand("deathrun").setExecutor(new CommandDeathRun());
        getCommand("leave").setExecutor(new CommandLeave());
        getCommand("start").setExecutor(new CommandStart());

        /* Update Info */
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &f&l* &7Version: &f" + this.getDescription().getVersion()));

        /* Setup Mode Checking */
        if (Objects.isNull(this.configuration.getMapConfig().isSetup()))
            this.configuration.getMapConfig().setSetup(true);

        /* Event Registry */
        List<Listener> listeners = (this.configuration.getMapConfig().isSetup() ? Collections.singletonList(
                new WandClickListener()
        ) : Arrays.asList(
                new ArenaButtonClickEvent(),
                new ArenaTrapTntUseEvent(),
                new ArenaPlayerActionEvent(),
                new ArenaPlayerCheckpointEvent(),
                new ArenaPlayerDamageEvent(),
                new ArenaPlayerJoinEvent(),
                new ArenaPlayerLoginEvent(),
                new ArenaPlayerMoveAtBlockEvent(),
                new ArenaPlayerQuitEvent(),
                new ArenaServerPingEvent(),
                new ArenaPlayerUseStrafeEvent(),
                new ArenaPlayerUseItemEvent()
        ));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &f&l* &7Loaded listeners successfully."));

        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener, Main.instance()));

        /* Task Registry */
        if (!this.configuration.getMapConfig().isSetup()) {
            new TrapCooldownTask().runTaskTimer(this, 0, 20);
            new ArenaMainTask().runTaskTimer(this, 0, 20);
            this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &f&l* &7Loaded tasks successfully."));
        }

        /* World Settings */
        if (!this.configuration.getMapConfig().isSetup())
            this.getServer().getWorld(this.configuration.getMapConfig().getWaitingLobby().getWorld()).setAutoSave(false);

        /* Strafe Registry */
        if (!this.configuration.getMapConfig().isSetup()) {
            Strafe.getStrafes().put(Strafe.Type.LEFT, new StrafeLeft());
            Strafe.getStrafes().put(Strafe.Type.RIGHT, new StrafeRight());
            Strafe.getStrafes().put(Strafe.Type.BACK, new StrafeBack());
        }

        /* Hooks */
        if (!this.configuration.getMapConfig().isSetup()) {

            if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {

                new PlaceholderHook().register();
                this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &f&l* &7Hooked PlaceholderAPI support."));

            }

        }

        /* Version Support */
        if (this.configuration.getSettings().getSounds().getArenaStarting().startsWith("NOTE_") && !Bukkit.getBukkitVersion().split("-")[0].startsWith("1.8"))
            this.getLogger().severe("Sound system may not work because configuration values for this version is incorrect.");


        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &f&l* &7Registered channels."));

        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r   &6&l* &7Loading finished in &a" + (System.currentTimeMillis() - currentTime) + "ms&7."));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&m------------------------------------"));
        this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&r"));

    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }

    public static Main instance() {
        return instance;
    }

}
