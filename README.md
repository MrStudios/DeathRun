<!-- HEADER INFO -->
<center>
    <h2>PLANNED UPDATE (PLUGIN REWRITE)</h2>
    <p>When? Currently is delayed but not cancelled.</p>
</center>

<!-- INFORMATIONS -->
<center>
    <h2>INFORMATIONS</h2>
    <p><strong>Death Run</strong> is a minigame that first appeared on the HiveMC server and became popular right away. In this game idea is that as death we have to make the game difficult for runners who will try to reach finish line in shortest time possible.</p>
</center>

<!-- TRAPS -->
<center>
    <h2>TRAPS</h2>
    <p>Below are list of available traps.</p>
</center>

<br>
<details>
    <summary>Trap Blocks</summary>
</details>
<br>
<details>
    <summary>Trap TNT</summary>
</details>
<br>
<details>
    <summary>Trap Giant</summary>
</details>
<br>
<details>
    <summary>Trap Lava</summary>
</details>
<br>
<details>
    <summary>Trap Water</summary>
</details>
<br>
<details>
    <summary>Trap Wall</summary>
</details>
<br>
<details>
    <summary>Trap Pistons</summary>
</details>

<!-- CONFIGURATION -->
<center>
    <h2>CONFIGURATION</h2>
</center>

```json
{
  "settings": {
    "server": {
      "thisServer": "dr-arena-1",
      "lobbyServer": "lobby"
    },
    "arena": {
      "limits": {
        "minPlayers": 5,
        "maxPlayers": 16,
        "maxDeathPlayers": 1
      },
      "timers": {
        "arenaInGameTime": 300,
        "arenaStartingTime": 30,
        "arenaEndMoveServerDelay": 15,
        "arenaTrapDelay": 20,
        "arenaStrafeDelay": 50
      },
      "effects": {
        "jumpBoostAmplifier": 15,
        "jumpBoostTime": 1.5,
        "speedAmplifier": 5,
        "speedTime": 5
      }
    },
    "sounds": {
      "arenaStarting": "NOTE_PLING",
      "arenaStartGlass": "ORB_PICKUP",
      "arenaStartGlassRemoved": "ENDERDRAGON_GROWL",
      "arenaCheckpointReached": "ORB_PICKUP",
      "arenaTrapDelayEvent": "VILLAGER_NO",
      "arenaPlayerStrafeUse": "BLAZE_BREATH",
      "arenaPlayerDeath": "SKELETON_DEATH"
    }
  },
  "messages": {
    "chat": {
      "noPermissions": "&cYou don't have permissions to this command.",
      "playerJoined": "&7<player> &ehas joined. &b(<currentPlayers>/<maxPlayers>)",
      "playerLeft": "&7<player> &ehas quit.",
      "startingTimer": "&eThe game starts in &6<startTimer> seconds&e.",
      "playerFinishedGame": "&r &f&lFINISH > &7Player &6<player> &7has finished game with &f<finishTime> &7time. &8(#<finishPosition>)",
      "commandStartCantUsageAtThisMoment": "&cYou can't use this command at this moment.",
      "startShorted": "&aStart has been shorted to &e<startTimer> seconds &aby &e<player>&a.",
      "gameStartRunner": [
        "&r",
        "&r   &6&l* &7You are &aRunner&7.",
        "&r   &f&l* &7Your task is complete run in shortest possible time, during this task interfering player will trigger various traps.",
        "&r"
      ],
      "gameStartDeath": [
        "&r",
        "&r   &6&l* &7You are &cDeath&7.",
        "&r   &f&l* &7Your task is to disturb runners by launching traps.",
        "&r"
      ],
      "gameEndSpectator": [
        "&r",
        "&r   &6&l* &7You are &8Spectator&7.",
        "&r   &f&l* &7Now you can follow other players.",
        "&r"
      ]
    },
    "title": {
      "startingTitle": "&c<startTimer>",
      "startingSubTitle": "&r",
      "deathTitle": "&cYOU DIED!",
      "deathSubTitle": "&eTry again!",
      "checkpointTitle": "&eCheckpoint #<checkpointNumber>",
      "checkpointSubTitle": "&r",
      "finishTitle": "&3&lFINISH",
      "finishSubTitle": "&7Finished game at &f#<finishPosition>&7.",
      "gameEndTitle": "&c&lGAME END!",
      "gameEndSubTitle": "&r",
      "endMoveServerTitle": "&bWaiting..",
      "endMoveServerSubTitle": "&7You will be transfered to lobby in &f<endTimer> seconds&7."
    },
    "scoreboard": {
      "title": "&e&lDEATH RUN",
      "waiting": [
        "&r",
        "&fMap: &a<mapName>",
        "&fPlayers: &a<currentPlayers>/<maxPlayers>",
        "&r",
        "&fWaiting..",
        "&r",
        "&ewww.mrstudios.pl"
      ],
      "starting": [
        "&r",
        "&fMap: &a<mapName>",
        "&fPlayers: &a<currentPlayers>/<maxPlayers>",
        "&r",
        "&fStart in &a<startTimer> seconds",
        "&r",
        "&ewww.mrstudios.pl"
      ],
      "playing": [
        "&r",
        "&fTime: &a<timeLeft>",
        "&fRole: &a<playerRole>",
        "&r",
        "&fRunners: &a<runnersLeft>",
        "&fDeaths: &c<deathsAmount>",
        "&r",
        "&fMap: &a<mapName>",
        "&r",
        "&ewww.mrstudios.pl"
      ]
    },
    "items": {
      "teleport": "&aTeleport &7(Right Click)",
      "strafeLeftAvailable": "&aStrafe Left &7(Right Click)",
      "strafeBackAvailable": "&aStrafe Back &7(Right Click)",
      "strafeRightAvailable": "&aStrafe Right &7(Right Click)",
      "strafeLeftDelay": "&aStrafe Left &8(<strafeDelay> seconds)",
      "strafeBackDelay": "&aStrafe Back &8(<strafeDelay> seconds)",
      "strafeRightDelay": "&aStrafe Right &8(<strafeDelay> seconds)",
      "leave": "&cLeave &7(Right Click)"
    },
    "holograms": {
      "trapDelayHologram": "&c<trapDelayTimer> seconds"
    },
    "roles": {
      "lobby": {
        "tabListPrefix": "&7"
      },
      "runner": {
        "displayName": "&aRunner",
        "tabListPrefix": "&7"
      },
      "death": {
        "displayName": "&cDeath",
        "tabListPrefix": "&7"
      },
      "spectator": {
        "displayName": "&7Spectator",
        "tabListPrefix": "&8[SPECTATOR] &7"
      }
    },
    "kick": {
      "serverIsFull": "&cThis arena is full.",
      "gameInProgress": "&cThis arena is in play."
    }
  },
  "configVersion": 4
}
```

<!-- PLACEHOLDERS -->
<center>
    <h2>PLACEHOLDERS</h2>
    <p>Placeholder list for <a href="https://github.com/PlaceholderAPI/PlaceholderAPI" style="text-decoration: none; color: #ffffff; font-weight: bold;">PlaceholderAPI</a> plugin.</p>
</center>

```
%deathrun_arena_name%       | Return player current arena name.     (String)
%deathrun_role_name%        | Return player current role in game.   (String)
%deathrun_role%             | Return player current role in game.   (Enum)
%deathrun_deaths%           | Return player deaths count.           (Integer)
%deathrun_checkpoint%       | Return player checkpoint number.      (Integer)
%deathrun_finish_position%  | Return player finish position.        (Integer)
%deathrun_version%          | Return plugin version.                (String)
%deathrun_runner_amount%    | Return amount of runners in arena.    (Integer)
%deathrun_death_amount%     | Return amount of death in arena.      (Integer)
%deathrun_spectator_amount% | Return amount of spectators in arena. (Integer)
%deathrun_players_amount%   | Return amount of players in arena.    (Integer)
%deathrun_gamestate%        | Return arena gamestate.               (Enum)
```

<!-- DISCORD -->
<center>
    <h2>DISCORD SERVER</h2>
    <a href="https://discord.com/invite/C8dF6zkYff" style="text-decoration: none;">
        <img src="https://discord.com/api/guilds/908864960698921000/widget.png?style=banner2">
    </a>
</center>

#
