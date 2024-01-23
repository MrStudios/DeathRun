package pl.mrstudios.deathrun.arena;

import me.catcoder.sidebar.ProtocolSidebar;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.mrstudios.commons.bukkit.item.ItemBuilder;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaGameStateChangeEvent;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaShutdownStartedEvent;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaRoleAssignedEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.util.ChannelUtil;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ArenaServiceRunnable extends BukkitRunnable {

    private final Arena arena;
    private final Plugin plugin;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    private BukkitTask sidebarTask;

    @Inject
    public ArenaServiceRunnable(Arena arena, Plugin plugin, Server server, MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {

        this.arena = arena;
        this.server = server;
        this.plugin = plugin;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
        this.setState(GameState.WAITING);

        this.barrierTimer = configuration.plugin().arenaStartingTime;
        this.arena.setRemainingTime(configuration.plugin().arenaGameTime);
        this.startingTimer = configuration.plugin().arenaPreStartingTime + 1;
        this.endDelayTimer = this.configuration.plugin().arenaEndDelay + 5;

    }

    @Override
    public void run() {

        switch (this.arena.getGameState()) {

            case WAITING ->
                this.waiting();

            case STARTING ->
                this.starting();

            case PLAYING ->
                this.playing();

            case ENDING ->
                this.ending();

        }

    }

    /* Waiting */
    protected void waiting() {

        if (this.arena.getUsers().size() >= this.configuration.plugin().arenaMinPlayers)
            this.setState(GameState.STARTING);

    }

    protected void stateSwitchToWaiting() {
        this.startingTimer = this.configuration.plugin().arenaPreStartingTime + 1;
    }

    /* Starting */
    private int startingTimer;

    protected void starting() {

        if (this.arena.getUsers().size() < this.configuration.plugin().arenaMinPlayers) {
            this.setState(GameState.WAITING);
            return;
        }

        this.startingTimer--;
        if (Arrays.stream(messageTimes).anyMatch((i) -> this.startingTimer == i))
            this.arena.getUsers()
                    .forEach((user) -> {

                        Player player = user.asBukkit();

                        if (player == null)
                            return;

                        this.audiences.player(player).showTitle(Title.title(
                                this.miniMessage.deserialize(
                                        this.configuration.language().arenaPreStartingTitle
                                                .replace("<timer>", String.valueOf(this.startingTimer))
                                ),
                                this.miniMessage.deserialize(
                                        this.configuration.language().arenaPreStartingSubtitle
                                                .replace("<timer>", String.valueOf(this.startingTimer))
                                ),
                                Title.Times.of(Duration.ofMillis(250), Duration.ofMillis(1000), Duration.ofMillis(250))
                        ));
                        this.audiences.player(player).sendMessage(this.miniMessage.deserialize(
                                this.configuration.language().chatMessageArenaStartingTimer
                                        .replace("<timer>", String.valueOf(this.startingTimer))
                        ));
                        player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundPreStarting, 1.0f, 1.0f);

                    });

        if (this.startingTimer > 0)
            return;

        this.setState(GameState.PLAYING);

    }

    protected void stateSwitchToStarting() {}

    /* Playing */
    private int barrierTimer;

    protected void playing() {

        if (this.barrierTimer != -1) {

            if (this.barrierTimer >= 0)
                this.barrierTimer--;

            if (Arrays.stream(messageTimes).anyMatch((i) -> this.barrierTimer == i))
                this.arena.getUsers()
                        .stream()
                        .map(IUser::asBukkit)
                        .filter(Objects::nonNull)
                        .forEach((player) -> {

                            player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundStarting, 1.0f, 1.0f);
                            this.audiences.player(player).showTitle(Title.title(
                                    this.miniMessage.deserialize(
                                            this.configuration.language().arenaStartingTitle
                                                    .replace("<timer>", String.valueOf(this.barrierTimer))),
                                    this.miniMessage.deserialize(
                                            this.configuration.language().arenaStartingSubtitle
                                                    .replace("<timer>", String.valueOf(this.barrierTimer))
                                    ),
                                    Title.Times.of(Duration.ofMillis(250), Duration.ofMillis(1000), Duration.ofMillis(250))
                            ));

                        });

            if (this.barrierTimer == 0) {
                this.configuration.map().arenaStartBarrierBlocks
                        .stream()
                        .map(Location::getBlock)
                        .forEach((block) -> block.setType(Material.AIR));
                this.arena.getUsers()
                        .stream()
                        .map(IUser::asBukkit)
                        .filter(Objects::nonNull)
                        .forEach((player) -> player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundStarted, 1.0f, 1.0f));
            }

            if (this.barrierTimer > 0)
                return;

        }

        this.arena.setElapsedTime(this.arena.getElapsedTime() + 1);
        this.arena.setRemainingTime(this.arena.getRemainingTime() - 1);

        if (this.arena.getRemainingTime() <= 0)
            this.setState(GameState.ENDING);

        if (this.arena.getRunners().isEmpty())
            this.setState(GameState.ENDING);

    }

    protected void stateSwitchToPlaying() {

        for (int i = 0; i < this.configuration.plugin().arenaDeathsAmount; i++)
            this.arena.getUsers().get(ThreadLocalRandom.current().nextInt(this.arena.getUsers().size())).setRole(Role.DEATH);

        this.arena.getUsers()
                .stream()
                .filter((user) -> user.getRole() != Role.DEATH)
                .forEach((user) -> user.setRole(Role.RUNNER));

        IntStream.range(0, this.arena.getRunners().size())
                .filter((i) -> this.arena.getRunners().get(i).asBukkit() != null)
                .forEach((i) -> Objects.requireNonNull(this.arena.getRunners().get(i).asBukkit()).teleport(this.configuration.map().arenaRunnerSpawnLocations.get(i)));

        IntStream.range(0, this.arena.getDeaths().size())
                .filter((i) -> this.arena.getDeaths().get(i).asBukkit() != null)
                .forEach((i) -> Objects.requireNonNull(this.arena.getDeaths().get(i).asBukkit()).teleport(this.configuration.map().arenaDeathSpawnLocations.get(i)));

        this.arena.getUsers()
                .forEach((user) -> {

                    Player player = user.asBukkit();
                    if (player == null)
                        return;

                    if (user.getRole() == Role.RUNNER)
                        this.configuration.language().chatMessageArenaGameStartRunner.stream()
                                .map(this.miniMessage::deserialize)
                                .forEach((component) -> this.audiences.player(player).sendMessage(component));

                    if (user.getRole() == Role.DEATH)
                        this.configuration.language().chatMessageArenaGameStartDeath.stream()
                                .map(this.miniMessage::deserialize)
                                .forEach((component) -> this.audiences.player(player).sendMessage(component));

                    user.setCheckpoint(this.configuration.map().arenaCheckpoints.get(0));
                    if (user.getRole() == Role.DEATH)
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, this.configuration.plugin().arenaDeathSpeedAmplifier, false, false, false));

                    this.server.getPluginManager().callEvent(new UserArenaRoleAssignedEvent(user.getRole(), user));
                    player.getInventory().clear();

                    if (user.getRole() == Role.RUNNER)
                        this.configuration.plugin().boosters
                                .forEach((booster) ->
                                        player.getInventory().setItem(
                                                booster.slot(), new ItemBuilder(booster.item().material())
                                                        .name(this.miniMessage.deserialize(booster.item().name()))
                                                        .texture((booster.item().texture() != null) ? Objects.requireNonNull(booster.item().texture()) : "")
                                                        .itemFlags(ItemFlag.values())
                                                        .build()
                                        ));

                });

    }

    /* Ending */
    private int endDelayTimer;

    protected void ending() {

        this.endDelayTimer--;
        if (this.endDelayTimer > this.configuration.plugin().arenaEndDelay)
            return;

        if (this.endDelayTimer > 0)
            this.arena.getUsers()
                    .stream()
                    .map(IUser::asBukkit)
                    .filter(Objects::nonNull)
                    .forEach(
                            (player) ->
                                    this.audiences.player(player).showTitle(Title.title(
                                            this.miniMessage.deserialize(
                                                    this.configuration.language().arenaMoveServerTitle
                                                            .replace("<endTimer>", String.valueOf(this.endDelayTimer))),
                                            this.miniMessage.deserialize(
                                                    this.configuration.language().arenaMoveServerSubtitle
                                                            .replace("<endTimer>", String.valueOf(this.endDelayTimer))
                                            ),
                                            Title.Times.of(Duration.ofMillis(250), Duration.ofMillis(1000), Duration.ofMillis(250))
                                    ))
                    );

        if (this.endDelayTimer > 0)
            return;

        ArenaShutdownStartedEvent event = new ArenaShutdownStartedEvent(this.arena, true);
        this.server.getPluginManager().callEvent(event);

        if (!event.isDefaultProcedure())
            return;

        this.arena.getUsers()
                .stream()
                .map(IUser::asBukkit)
                .forEach((player) -> ChannelUtil.connect(this.plugin, player, this.configuration.plugin().server));

        if (this.endDelayTimer > -5)
            return;

        this.server.shutdown();

    }

    protected void stateSwitchToEnding() {

        this.arena.getUsers()
                .stream()
                .map(IUser::asBukkit)
                .filter(Objects::nonNull)
                .forEach((player) ->
                        this.audiences.player(player).showTitle(Title.title(
                                this.miniMessage.deserialize(this.configuration.language().arenaGameEndTitle),
                                this.miniMessage.deserialize(this.configuration.language().arenaGameEndSubtitle),
                                Title.Times.of(Duration.ofMillis(250), Duration.ofMillis(2500), Duration.ofMillis(250))
                        ))
                );

    }

    /* Internal */
    protected void setState(GameState gameState) {

        this.arena.setGameState(gameState);

        switch (gameState) {

            case WAITING ->
                this.stateSwitchToWaiting();

            case STARTING ->
                this.stateSwitchToStarting();

            case PLAYING ->
                this.stateSwitchToPlaying();

            case ENDING ->
                this.stateSwitchToEnding();

        }

        if (this.sidebarTask != null)
            this.sidebarTask.cancel();

        if (this.arena.getSidebar() != null)
            this.arena.getSidebar().destroy();

        this.server.getPluginManager().callEvent(new ArenaGameStateChangeEvent(this.arena, this.arena.getGameState()));
        if (this.arena.getGameState() == GameState.ENDING)
            return;

        this.arena.setSidebar(ProtocolSidebar.newAdventureSidebar(this.miniMessage.deserialize(this.configuration.language().arenaScoreboardTitle), this.plugin));

        switch (this.arena.getGameState()) {

            case WAITING ->
                this.configuration.language().arenaScoreboardLinesWaiting.forEach(this::addLine);

            case STARTING ->
                this.configuration.language().arenaScoreboardLinesStarting.forEach(this::addLine);

            case PLAYING ->
                this.configuration.language().arenaScoreboardLinesPlaying.forEach(this::addLine);

        }

        this.arena.getUsers()
                .stream()
                .map(IUser::asBukkit)
                .filter(Objects::nonNull)
                .forEach(this.arena.getSidebar()::addViewer);

        this.sidebarTask = this.arena.getSidebar().updateLinesPeriodically(0, 20);

    }

    protected void addLine(String content) {
        this.arena.getSidebar().addUpdatableLine((player) -> {

            IUser user = this.arena.getUser(player);

            if (user == null)
                this.arena.getSidebar().removeViewer(player);

            if (user == null)
                return Component.empty();

            return this.miniMessage.deserialize(
                    content.replace("<map>", this.configuration.map().arenaName)
                            .replace("<role>", this.rolePrefix(user.getRole()))
                            .replace("<currentPlayers>", String.valueOf(this.arena.getUsers().size()))
                            .replace("<maxPlayers>", String.valueOf(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size()))
                            .replace("<timer>", String.valueOf(this.startingTimer))
                            .replace("<time>", String.valueOf(this.arena.getRemainingTime()))
                            .replace("<timeFormatted>", this.formatTime(this.arena.getRemainingTime()))
                            .replace("<runners>", String.valueOf(this.arena.getRunners().size()))
                            .replace("<deaths>", String.valueOf(user.getDeaths()))
            );

        });
    }

    protected String rolePrefix(Role role) {
        return switch (role) {

            case RUNNER ->
                    this.configuration.language().arenaRolesRunnerName;

            case DEATH ->
                    this.configuration.language().arenaRolesDeathName;

            case SPECTATOR ->
                    this.configuration.language().arenaRolesSpectatorName;

            default ->
                    role.name();

        };
    }

    protected String formatTime(int time) {
        int minutes = time / 60, seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /* Constants */
    protected static final int[] messageTimes = new int[] { 1, 2, 3, 4, 5, 10, 15, 30, 60, 90, 180, 360 };

}
