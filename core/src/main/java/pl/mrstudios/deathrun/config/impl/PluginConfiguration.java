package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Material;
import org.bukkit.Sound;

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
public class PluginConfiguration extends OkaeriConfig {

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                               GENERAL",
            "------------------------------------------------------------------------",
            ""
    })
    @Comment({ "Lobby Server Name" })
    @CustomKey("lobby_server_name")
    public String SERVER = "dr-lobby-1";

    @Comment({ "", "Minimum amount of players that is needed to game start." })
    @CustomKey("arena_min_players")
    public int ARENA_MIN_PLAYERS = 5;

    @Comment({ "", "Amount of players with 'DEATH' role on arena." })
    @CustomKey("arena_deaths_amount")
    public int ARENA_DEATHS_AMOUNT = 1;

    @Comment({ "", "Amount of time that runners have to complete run. (in seconds)" })
    @CustomKey("arena_game_time")
    public int ARENA_GAME_TIME = 600;

    @Comment({ "", "Amount of time that is needed to game start." })
    @CustomKey("arena_pre_starting_time")
    public int ARENA_PRE_STARTING_TIME = 30;

    @Comment({ "", "Amount of time before start barrier will be removed." })
    @CustomKey("arena_starting_time")
    public int ARENA_STARTING_TIME = 10;

    @Comment({ "", "Amount of time before players on server will be moved to lobby." })
    @CustomKey("arena_end_delay")
    public int ARENA_END_DELAY = 10;

    @Comment({ "", "Amount of time before trap can be used again." })
    @CustomKey("arena_trap_delay")
    public int ARENA_TRAP_DELAY = 20;

    @Comment({ "", "Amount of time before strafe can be used again." })
    @CustomKey("arena_strafe_delay")
    public int ARENA_STRAFE_DELAY = 30;

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                EFFECTS",
            "------------------------------------------------------------------------",
            ""
    })
    @Comment({ "Jump Boost Effect" })
    @CustomKey("effects_jump_boost_block")
    public Material EFFECTS_JUMP_BOOST_BLOCK = Material.EMERALD_BLOCK;

    @CustomKey("effects_jump_boost_amplifier")
    public int EFFECTS_JUMP_BOOST_AMPLIFIER = 5;

    @CustomKey("effects_jump_boost_duration")
    public float EFFECTS_JUMP_BOOST_DURATION = 1.5f;

    @Comment({ "", "Speed Effect" })
    @CustomKey("effects_speed_block")
    public Material EFFECTS_SPEED_BLOCK = Material.REDSTONE_BLOCK;

    @CustomKey("effects_speed_amplifier")
    public int EFFECTS_SPEED_AMPLIFIER = 5;

    @CustomKey("effects_speed_duration")
    public float EFFECTS_SPEED_DURATION = 1.5f;

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                               SOUNDS",
            "------------------------------------------------------------------------",
            ""
    })
    @CustomKey("arena_sound_pre_starting")
    public Sound ARENA_SOUND_PRE_STARTING = Sound.BLOCK_NOTE_BLOCK_PLING;

    @CustomKey("arena_sound_starting")
    public Sound ARENA_SOUND_STARTING = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

    @CustomKey("arena_sound_started")
    public Sound ARENA_SOUND_STARTED = Sound.ENTITY_ENDER_DRAGON_GROWL;

    @CustomKey("arena_sound_checkpoint_reached")
    public Sound ARENA_SOUND_CHECKPOINT_REACHED = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

    @CustomKey("arena_sound_trap_delay")
    public Sound ARENA_SOUND_TRAP_DELAY = Sound.ENTITY_VILLAGER_NO;

    @CustomKey("arena_sound_strafe_use")
    public Sound ARENA_SOUND_STRAFE_USE = Sound.ENTITY_BLAZE_AMBIENT;

    @CustomKey("arena_sound_player_death")
    public Sound ARENA_SOUND_PLAYER_DEATH = Sound.ENTITY_SKELETON_DEATH;

}
