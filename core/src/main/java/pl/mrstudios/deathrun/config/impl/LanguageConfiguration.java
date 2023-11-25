package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.List;

@Header({
        " ",
        "------------------------------------------------------------------------",
        "                              INFORMATION",
        "------------------------------------------------------------------------",
        " ",
        " This is configuration file for DeathRun plugin, if you found any issue ",
        " contact with us through Discord or create issue on GitHub. If you need",
        " help with configuration visit https://mrstudios.pl/documentation.",
        " "
}) @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class LanguageConfiguration extends OkaeriConfig {

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                 GENERAL",
            "------------------------------------------------------------------------",
            ""
    })
    public String chatMessageNoPermissions = "<red>You don't have permissions to this command.";
    public String chatMessageArenaPlayerJoined = "<gray><player> <yellow>has joined. <aqua>(<currentPlayers>/<maxPlayers>)";
    public String chatMessageArenaPlayerLeft = "<gray><player> <yellow>has quit.";
    public String chatMessageArenaStartingTimer = "<yellow>Game starts in <gold><timer> seconds<yellow>.";
    public String chatMessageArenaPlayerFinished = "&r <white><b>FINISH > <gray>Player <gold><player> <gray>has finished game in <white><seconds> seconds<gray>. <dark_gray>(#<finishPosition>)";

    public List<String> chatMessageArenaGameStartRunner = List.of(
            "&r",
            "&r   <gold><b>* <gray>You are <green>Runner<gray>.",
            "&r   <white><b>* <gray>Your task is complete run in shortest possible time, during this task interfering player will trigger various traps.",
            "&r"
    );

    public List<String> chatMessageArenaGameStartDeath = List.of(
            "&r",
            "&r   <gold><b>* <gray>You are <red>Death<gray>.",
            "&r   <white><b>* <gray>Your task is to disturb runners by launching traps.",
            "&r"
    );

    public List<String> chatMessageGameEndSpectator = List.of(
            "&r",
            "&r   <gold><b>* <gray>You are <dark_gray>Spectator<gray>.",
            "&r   <white><b>* <gray>Now you can follow other players.",
            "&r"
    );

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                 TITLES",
            "------------------------------------------------------------------------",
            ""
    })
    public String arenaPreStartingTitle = "<red><timer>";
    public String arenaPreStartingSubtitle = "&r";

    public String arenaStartingTitle = "<red><timer>";
    public String arenaStartingSubtitle = "&r";

    public String arenaDeathTitle = "<red>YOU DIED!";
    public String arenaDeathSubtitle = "<yellow>Don't give up! Try again!";

    public String arenaCheckpointTitle = "<yellow>CHECKPOINT!";
    public String arenaCheckpointSubtitle = "<gold>You reached <yellow>#<checkpoint> checkpoint<gold>.";

    public String arenaFinishTitle = "<dark_aqua><b>FINISH";
    public String arenaFinishSubtitle = "<gray>Your position is <white>#<position><gray>.";

    public String arenaGameEndTitle = "<red><b>GAME END!";
    public String arenaGameEndSubtitle = "&r";

    public String arenaMoveServerTitle = "<aqua>Waiting..";
    public String arenaMoveServerSubtitle = "<gray>You will be transferred to lobby in <white><endTimer> seconds<gray>.";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                              SCOREBOARD",
            "------------------------------------------------------------------------",
            ""
    })
    public String arenaScoreboardTitle = "<yellow><b>DEATH RUN";

    public List<String> arenaScoreboardLinesWaiting = List.of(
            "&r",
            "<white>Map: <green><map>",
            "<white>Players: <green><currentPlayers>/<maxPlayers>",
            "&r",
            "<white>Waiting..",
            "&r",
            "<yellow>www.mrstudios.pl"
    );

    public List<String> arenaScoreboardLinesStarting = List.of(
            "&r",
            "<white>Map: <green><map>",
            "<white>Players: <green><currentPlayers>/<maxPlayers>",
            "&r",
            "<white>Start in <green><timer> seconds",
            "&r",
            "<yellow>www.mrstudios.pl"
    );

    public List<String> arenaScoreboardLinesPlaying = List.of(
            "&r",
            "<white>Time: <green><time>",
            "<white>Role: <green><role>",
            "&r",
            "<white>Runners: <green><runners>",
            "<white>Deaths: <red><deaths>",
            "&r",
            "<white>Map: <green><map>",
            "&r",
            "<yellow>www.mrstudios.pl"
    );

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                              HOLOGRAMS",
            "------------------------------------------------------------------------",
            ""
    })
    public String arenaHologramTrapDelayed = "<red><delay> seconds";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                ROLES",
            "------------------------------------------------------------------------",
            ""
    })
    public String arenaRolesLobbyTabPrefix = "<gray>";

    @Comment("")
    public String arenaRolesRunnerName = "<green>Runner";
    public String arenaRolesRunnerTabPrefix = "<gray>";

    @Comment("")
    public String arenaRolesDeathName = "<red>Death";
    public String arenaRolesDeathTabPrefix = "<gray>";

    @Comment("")
    public String arenaRolesSpectatorName = "<gray>Spectator";
    public String arenaRolesSpectatorTabPrefix = "<dark_gray>[SPECTATOR] <gray>";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                ITEMS",
            "------------------------------------------------------------------------",
            ""
    })
    public String arenaItemTeleportName = "<green>Teleport <gray>(Right Click)";

    public String arenaItemStrafeLeftAvailableName = "<green>Strafe Left <gray>(Right Click)";
    public String arenaItemStrafeLeftUnavailableName = "<green>Strafe Left <dark_gray>(<delay> seconds)";

    public String arenaItemStrafeBackAvailableName = "<green>Strafe Back <gray>(Right Click)";
    public String arenaItemStrafeBackUnavailableName = "<green>Strafe Back <dark_gray>(<delay> seconds)";

    public String arenaItemStrafeRightAvailableName = "<green>Strafe Right <gray>(Right Click)";
    public String arenaItemStrafeRightUnavailableName = "<green>Strafe Right <dark_gray>(<delay> seconds)";

    public String arenaItemLeaveName = "<red>Leave <gray>(Right Click)";

}