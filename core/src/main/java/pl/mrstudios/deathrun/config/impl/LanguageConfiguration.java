package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;

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
})
public class LanguageConfiguration extends OkaeriConfig {

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                 GENERAL",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("chat_message_no_permissions")
    public String CHAT_MESSAGE_NO_PERMISSIONS = "&cYou don't have permissions to this command.";

    @CustomKey("chat_message_arena_player_joined")
    public String CHAT_MESSAGE_ARENA_PLAYER_JOINED = "&7<player> &ehas joined. &b(<currentPlayers>/<maxPlayers>)";

    @CustomKey("chat_message_arena_player_left")
    public String CHAT_MESSAGE_ARENA_PLAYER_LEFT = "&7<player> &ehas quit.";

    @CustomKey("chat_message_arena_starting_timer")
    public String CHAT_MESSAGE_ARENA_STARTING_TIMER = "&eGame starts in &6<timer> seconds&e.";

    @CustomKey("chat_message_arena_player_finished")
    public String CHAT_MESSAGE_ARENA_PLAYER_FINISHED = "&r &f&lFINISH > &7Player &6<player> &7has finished game in &f<seconds> seconds&7. &8(#<finishPosition>)";

    @CustomKey("chat_message_arena_game_start_runner")
    public List<String> CHAT_MESSAGE_ARENA_GAME_START_RUNNER = List.of(
            "&r",
            "&r   &6&l* &7You are &aRunner&7.",
            "&r   &f&l* &7Your task is complete run in shortest possible time, during this task interfering player will trigger various traps.",
            "&r"
    );

    @CustomKey("chat_message_arena_game_start_death")
    public List<String> CHAT_MESSAGE_ARENA_GAME_START_DEATH = List.of(
            "&r",
            "&r   &6&l* &7You are &cDeath&7.",
            "&r   &f&l* &7Your task is to disturb runners by launching traps.",
            "&r"
    );

    @CustomKey("chat_message_arena_game_end_spectator")
    public List<String> CHAT_MESSAGE_GAME_END_SPECTATOR = List.of(
            "&r",
            "&r   &6&l* &7You are &8Spectator&7.",
            "&r   &f&l* &7Now you can follow other players.",
            "&r"
    );

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                 TITLES",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_pre_starting_title")
    public String ARENA_PRE_STARTING_TITLE = "&c<timer>";

    @CustomKey("arena_pre_starting_subtitle")
    public String ARENA_PRE_STARTING_SUBTITLE = "&r";

    @CustomKey("arena_starting_title")
    public String ARENA_STARTING_TITLE = "&c<timer>";

    @CustomKey("arena_starting_subtitle")
    public String ARENA_STARTING_SUBTITLE = "&r";

    @CustomKey("arena_death_title")
    public String ARENA_DEATH_TITLE = "&cYOU DIED!";

    @CustomKey("arena_death_subtitle")
    public String ARENA_DEATH_SUBTITLE = "&eDon't give up! Try again!";

    @CustomKey("arena_checkpoint_title")
    public String ARENA_CHECKPOINT_TITLE = "&eCHECKPOINT!";

    @CustomKey("arena_checkpoint_subtitle")
    public String ARENA_CHECKPOINT_SUBTITLE = "&6You reached &e#<checkpoint> checkpoint&6.";

    @CustomKey("arena_finish_title")
    public String ARENA_FINISH_TITLE = "&3&lFINISH";

    @CustomKey("arena_finish_subtitle")
    public String ARENA_FINISH_SUBTITLE = "&7Your position is &f#<position>&7.";

    @CustomKey("arena_game_end_title")
    public String ARENA_GAME_END_TITLE = "&c&lGAME END!";

    @CustomKey("arena_game_end_subtitle")
    public String ARENA_GAME_END_SUBTITLE = "&r";

    @CustomKey("arena_move_server_title")
    public String ARENA_MOVE_SERVER_TITLE = "&bWaiting..";

    @CustomKey("arena_move_server_subtitle")
    public String ARENA_MOVE_SERVER_SUBTITLE = "&7You will be transferred to lobby in &f<endTimer> seconds&7.";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                              SCOREBOARD",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_scoreboard_title")
    public String ARENA_SCOREBOARD_TITLE = "&e&lDEATH RUN";

    @CustomKey("arena_scoreboard_lines_waiting")
    public List<String> ARENA_SCOREBOARD_LINES_WAITING = List.of(
            "&r",
            "&fMap: &a<map>",
            "&fPlayers: &a<currentPlayers>/<maxPlayers>",
            "&r",
            "&fWaiting..",
            "&r",
            "&ewww.mrstudios.pl"
    );

    @CustomKey("arena_scoreboard_lines_starting")
    public List<String> ARENA_SCOREBOARD_LINES_STARTING = List.of(
            "&r",
            "&fMap: &a<map>",
            "&fPlayers: &a<currentPlayers>/<maxPlayers>",
            "&r",
            "&fStart in &a<timer> seconds",
            "&r",
            "&ewww.mrstudios.pl"
    );

    @CustomKey("arena_scoreboard_lines_playing")
    public List<String> ARENA_SCOREBOARD_LINES_PLAYING = List.of(
            "&r",
            "&fTime: &a<time>",
            "&fRole: &a<role>",
            "&r",
            "&fRunners: &a<runners>",
            "&fDeaths: &c<deaths>",
            "&r",
            "&fMap: &a<map>",
            "&r",
            "&ewww.mrstudios.pl"
    );

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                              HOLOGRAMS",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_hologram_trap_delayed")
    public String ARENA_HOLOGRAM_TRAP_DELAYED = "&c<delay> seconds";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                ROLES",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_roles_lobby_tab_prefix")
    public String ARENA_ROLES_LOBBY_TAB_PREFIX = "&7";

    @Comment("")
    @CustomKey("arena_roles_runner_name")
    public String ARENA_ROLES_RUNNER_NAME = "&aRunner";

    @CustomKey("arena_roles_runner_tab_prefix")
    public String ARENA_ROLES_RUNNER_TAB_PREFIX = "&7";

    @Comment("")
    @CustomKey("arena_roles_death_name")
    public String ARENA_ROLES_DEATH_NAME = "&cDeath";

    @CustomKey("arena_roles_death_tab_prefix")
    public String ARENA_ROLES_DEATH_TAB_PREFIX = "&7";

    @Comment("")
    @CustomKey("arena_roles_spectator_name")
    public String ARENA_ROLES_SPECTATOR_NAME = "&7Spectator";

    @CustomKey("arena_roles_spectator_tab_prefix")
    public String ARENA_ROLES_SPECTATOR_TAB_PREFIX = "&8[SPECTATOR] &7";

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                ITEMS",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_item_teleport_name")
    public String ARENA_ITEM_TELEPORT_NAME = "&aTeleport &7(Right Click)";

    @CustomKey("arena_item_strafe_left_available_name")
    public String ARENA_ITEM_STRAFE_LEFT_AVAILABLE_NAME = "&aStrafe Left &7(Right Click)";

    @CustomKey("arena_item_strafe_left_unavailable_name")
    public String ARENA_ITEM_STRAFE_LEFT_UNAVAILABLE_NAME = "&aStrafe Left &8(<delay> seconds)";

    @CustomKey("arena_item_strafe_back_available_name")
    public String ARENA_ITEM_STRAFE_BACK_AVAILABLE_NAME = "&aStrafe Back &7(Right Click)";

    @CustomKey("arena_item_strafe_back_unavailable_name")
    public String ARENA_ITEM_STRAFE_BACK_UNAVAILABLE_NAME = "&aStrafe Back &8(<delay> seconds)";

    @CustomKey("arena_item_strafe_right_available_name")
    public String ARENA_ITEM_STRAFE_RIGHT_AVAILABLE_NAME = "&aStrafe Right &7(Right Click)";

    @CustomKey("arena_item_strafe_right_unavailable_name")
    public String ARENA_ITEM_STRAFE_RIGHT_UNAVAILABLE_NAME = "&aStrafe Right &8(<delay> seconds)";

    @CustomKey("arena_item_leave_name")
    public String ARENA_ITEM_LEAVE_NAME = "&cLeave &7(Right Click)";

}
