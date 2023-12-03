package pl.mrstudios.deathrun.config.impl;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
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
}) @Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfiguration extends OkaeriConfig {

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                               GENERAL",
            "------------------------------------------------------------------------",
            ""
    })
    @Comment({ "Lobby Server Name" })
    public String server = "dr-lobby-1";

    @Comment({ "", "Minimum amount of players that is needed to game start." })
    public int arenaMinPlayers = 5;

    @Comment({ "", "Amount of players with 'DEATH' role on arena." })
    public int arenaDeathsAmount = 1;

    @Comment({ "", "Amount of time that runners have to complete run. (in seconds)" })
    public int arenaGameTime = 600;

    @Comment({ "", "Amount of time that is needed to game start." })
    public int arenaPreStartingTime = 30;

    @Comment({ "", "Amount of time before start barrier will be removed." })
    public int arenaStartingTime = 10;

    @Comment({ "", "Amount of time before players on server will be moved to lobby." })
    public int arenaEndDelay = 10;

    @Comment({ "", "Amount of time before trap can be used again." })
    public int arenaTrapDelay = 20;

    @Comment({ "", "Amount of time before strafe can be used again." })
    public int arenaStrafeDelay = 30;

    @Comment({ "", "Max ,,survivable`` distance that player can fall." })
    public int arenaMaxFallDistance = 8;

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                                EFFECTS",
            "------------------------------------------------------------------------",
            ""
    })
    @Comment({ "Jump Boost Effect" })
    public Material effectsJumpBoostBlock = Material.EMERALD_BLOCK;
    public int effectsJumpBoostAmplifier = 5;
    public float effectsJumpBoostDuration = 1.5f;

    @Comment({ "", "Speed Effect" })
    public Material effectsSpeedBlock = Material.REDSTONE_BLOCK;
    public int effectsSpeedAmplifier = 5;
    public float effectsSpeedDuration = 1.5f;

    @Comment({
            "",
            "------------------------------------------------------------------------",
            "                               SOUNDS",
            "------------------------------------------------------------------------",
            ""
    })
    public Sound arenaSoundPreStarting = Sound.BLOCK_NOTE_BLOCK_PLING;
    public Sound arenaSoundStarting = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    public Sound arenaSoundStarted = Sound.ENTITY_ENDER_DRAGON_GROWL;
    public Sound arenaSoundCheckpointReached = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    public Sound arenaSoundTrapDelay = Sound.ENTITY_VILLAGER_NO;
    public Sound arenaSoundStrafeUse = Sound.ENTITY_BLAZE_AMBIENT;
    public Sound arenaSoundPlayerDeath = Sound.ENTITY_SKELETON_DEATH;

}
