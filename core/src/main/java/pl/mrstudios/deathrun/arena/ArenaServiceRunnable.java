package pl.mrstudios.deathrun.arena;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.bukkit.item.ItemBuilder;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaGameStateChangeEvent;
import pl.mrstudios.deathrun.api.arena.event.arena.ArenaShutdownStartedEvent;
import pl.mrstudios.deathrun.api.arena.event.user.UserArenaRoleAssignedEvent;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.valueOf;
import static java.time.Duration.ofMillis;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;
import static me.catcoder.sidebar.ProtocolSidebar.newAdventureSidebar;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.title.Title.Times.of;
import static net.kyori.adventure.title.Title.title;
import static org.bukkit.Material.AIR;
import static org.bukkit.inventory.ItemFlag.values;
import static pl.mrstudios.deathrun.api.arena.enums.GameState.*;
import static pl.mrstudios.deathrun.api.arena.user.enums.Role.DEATH;
import static pl.mrstudios.deathrun.api.arena.user.enums.Role.RUNNER;
import static pl.mrstudios.deathrun.util.ChannelUtil.connect;

public class ArenaServiceRunnable extends BukkitRunnable {

    private final Arena arena;
    private final Plugin plugin;
    private final Server server;
    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    private BukkitTask sidebarTask;

    @Inject
    public ArenaServiceRunnable(
            @NotNull Arena arena,
            @NotNull Plugin plugin,
            @NotNull Server server,
            @NotNull MiniMessage miniMessage,
            @NotNull BukkitAudiences audiences,
            @NotNull Configuration configuration
    ) {

        this.arena = arena;
        this.server = server;
        this.plugin = plugin;
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
        this.setState(WAITING);

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
            this.setState(STARTING);

    }

    protected void stateSwitchToWaiting() {
        this.startingTimer = this.configuration.plugin().arenaPreStartingTime + 1;
    }

    /* Starting */
    private int startingTimer;

    protected void starting() {

        if (this.arena.getUsers().size() < this.configuration.plugin().arenaMinPlayers) {
            this.setState(WAITING);
            return;
        }

        this.startingTimer--;
        if (stream(messageTimes).anyMatch((i) -> this.startingTimer == i))
            this.arena.getUsers().stream()
                    .map(IUser::asBukkit).filter(Objects::nonNull)
                    .forEach((player) -> {
                        this.audiences.player(player).showTitle(title(
                                this.miniMessage.deserialize(
                                        this.configuration.language().arenaPreStartingTitle
                                                .replace("<timer>", valueOf(this.startingTimer))
                                ),
                                this.miniMessage.deserialize(
                                        this.configuration.language().arenaPreStartingSubtitle
                                                .replace("<timer>", valueOf(this.startingTimer))
                                ),
                                of(ofMillis(250), ofMillis(1000), ofMillis(250))
                        ));
                        this.audiences.player(player).sendMessage(this.miniMessage.deserialize(
                                this.configuration.language().chatMessageArenaStartingTimer
                                        .replace("<timer>", valueOf(this.startingTimer))
                        ));
                        player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundPreStarting, 1.0f, 1.0f);
                    });

        if (this.startingTimer > 0)
            return;

        this.setState(PLAYING);

    }

    protected void stateSwitchToStarting() {}

    /* Playing */
    private int barrierTimer;

    protected void playing() {

        if (this.barrierTimer != -1) {

            if (this.barrierTimer >= 0)
                this.barrierTimer--;

            if (stream(messageTimes).anyMatch((i) -> this.barrierTimer == i))
                this.arena.getUsers()
                        .stream().map(IUser::asBukkit)
                        .filter(Objects::nonNull).forEach((player) -> {

                            player.playSound(player.getLocation(), this.configuration.plugin().arenaSoundStarting, 1.0f, 1.0f);
                            this.audiences.player(player).showTitle(title(
                                    this.miniMessage.deserialize(
                                            this.configuration.language().arenaStartingTitle
                                                    .replace("<timer>", valueOf(this.barrierTimer))),
                                    this.miniMessage.deserialize(
                                            this.configuration.language().arenaStartingSubtitle
                                                    .replace("<timer>", valueOf(this.barrierTimer))
                                    ),
                                    of(ofMillis(250), ofMillis(1000), ofMillis(250))
                            ));

                        });

            if (this.barrierTimer == 0) {

                this.configuration.map().arenaStartBarrierBlocks
                        .stream()
                        .map(Location::getBlock)
                        .forEach((block) -> block.setType(AIR));

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

        if (this.arena.getRemainingTime() <= 0 || this.arena.getRunners().isEmpty())
            this.setState(ENDING);

    }

    protected void stateSwitchToPlaying() {

        for (int i = 0; i < this.configuration.plugin().arenaDeathsAmount; i++)
            this.arena.getUsers().get(ThreadLocalRandom.current().nextInt(this.arena.getUsers().size())).setRole(DEATH);

        this.arena.getUsers()
                .stream()
                .filter((user) -> user.getRole() != DEATH)
                .forEach((user) -> user.setRole(RUNNER));

        range(0, this.arena.getRunners().size())
                .filter((i) -> this.arena.getRunners().get(i).asBukkit() != null)
                .forEach((i) -> requireNonNull(this.arena.getRunners().get(i).asBukkit()).teleport(this.configuration.map().arenaRunnerSpawnLocations.get(i)));

        range(0, this.arena.getDeaths().size())
                .filter((i) -> this.arena.getDeaths().get(i).asBukkit() != null)
                .forEach((i) -> requireNonNull(this.arena.getDeaths().get(i).asBukkit()).teleport(this.configuration.map().arenaDeathSpawnLocations.get(i)));

        this.arena.getUsers()
                .forEach((user) -> {

                    Player player = user.asBukkit();
                    if (player == null)
                        return;

                    if (user.getRole() == RUNNER)
                        this.configuration.language().chatMessageArenaGameStartRunner.stream()
                                .map(this.miniMessage::deserialize)
                                .forEach((component) -> this.audiences.player(player).sendMessage(component));

                    if (user.getRole() == DEATH)
                        this.configuration.language().chatMessageArenaGameStartDeath.stream()
                                .map(this.miniMessage::deserialize)
                                .forEach((component) -> this.audiences.player(player).sendMessage(component));

                    user.setCheckpoint(this.configuration.map().arenaCheckpoints.get(0));
                    if (user.getRole() == DEATH)
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, this.configuration.plugin().arenaDeathSpeedAmplifier, false, false, false));

                    this.server.getPluginManager().callEvent(new UserArenaRoleAssignedEvent(user.getRole(), user));
                    player.getInventory().clear();

                    if (user.getRole() == RUNNER)
                        this.configuration.plugin().boosters
                                .forEach((booster) ->
                                        player.getInventory().setItem(
                                                booster.slot(), new ItemBuilder(booster.item().material())
                                                        .name(this.miniMessage.deserialize(booster.item().name()))
                                                        .texture((booster.item().texture() != null) ? requireNonNull(booster.item().texture()) : "")
                                                        .itemFlags(values())
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
                                    this.audiences.player(player).showTitle(title(
                                            this.miniMessage.deserialize(
                                                    this.configuration.language().arenaMoveServerTitle
                                                            .replace("<endTimer>", valueOf(this.endDelayTimer))),
                                            this.miniMessage.deserialize(
                                                    this.configuration.language().arenaMoveServerSubtitle
                                                            .replace("<endTimer>", valueOf(this.endDelayTimer))
                                            ),
                                            of(ofMillis(250), ofMillis(1000), ofMillis(250))
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
                .forEach((player) -> connect(this.plugin, player, this.configuration.plugin().server));

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
                        this.audiences.player(player).showTitle(title(
                                this.miniMessage.deserialize(this.configuration.language().arenaGameEndTitle),
                                this.miniMessage.deserialize(this.configuration.language().arenaGameEndSubtitle),
                                of(ofMillis(250), ofMillis(2500), ofMillis(250))
                        ))
                );

    }

    /* Internal */
    protected void setState(@NotNull GameState gameState) {

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
        if (this.arena.getGameState() == ENDING)
            return;

        this.arena.setSidebar(newAdventureSidebar(this.miniMessage.deserialize(this.configuration.language().arenaScoreboardTitle), this.plugin));

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

    protected void addLine(@NotNull String content) {
        this.arena.getSidebar()
                .addUpdatableLine(
                        (player) -> {

                            AtomicReference<Component> component = new AtomicReference<>(empty());

                            ofNullable(this.arena.getUser(player))
                                    .ifPresentOrElse(
                                            (user) -> component.set(this.miniMessage.deserialize(
                                                    content.replace("<map>", this.configuration.map().arenaName)
                                                            .replace("<role>", this.rolePrefix(user.getRole()))
                                                            .replace("<currentPlayers>", valueOf(this.arena.getUsers().size()))
                                                            .replace("<maxPlayers>", valueOf(this.configuration.map().arenaRunnerSpawnLocations.size() + this.configuration.map().arenaDeathSpawnLocations.size()))
                                                            .replace("<timer>", valueOf(this.startingTimer))
                                                            .replace("<time>", valueOf(this.arena.getRemainingTime()))
                                                            .replace("<timeFormatted>", this.formatTime(this.arena.getRemainingTime()))
                                                            .replace("<runners>", valueOf(this.arena.getRunners().size()))
                                                            .replace("<deaths>", valueOf(user.getDeaths()))
                                            )),
                                            () -> this.arena.getSidebar().removeViewer(player)
                                    );

                            return component.get();

                        });
    }

    protected String rolePrefix(@NotNull Role role) {
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
