## IMPORTANT!
This is version under development, if you want stable version use [version/0.4-alpha](https://github.com/MrStudios/DeathRun/tree/version/0.4-alpha).

## WHAT IS THIS?
**Death Run** is a minigame that first appeared on **HiveMC Network** and become popular instantly. In this game idea is that as death we have to make the game difficult for runners who will try to reach finish line in shortest time possible.

## PROGRESS
- [ ] Game System
- [x] Development API
- [x] Arena Setup
- [x] Modern Version Support (1.16+)
- [ ] ~~Legacy Version Support (<1.16)~~ (Cancelled)
- [ ] Game Backend Server/Client (BungeeCord/Velocity + Netty Application)
- [ ] Extensions
- - [ ] Private Games
- - [ ] Map Voting
- - [ ] Statistics

## CONFIGURATION FILES
* Main Configuration File `(config.yml)`
```yaml
#  
# ------------------------------------------------------------------------
#                               INFORMATION
# ------------------------------------------------------------------------
#  
#  This is configuration file for DeathRun plugin, if you found any issue 
#  contact with us through Discord or create issue on GitHub. If you need
#  help with configuration visit https://mrstudios.pl/documentation.
#  

# ------------------------------------------------------------------------
#                                GENERAL
# ------------------------------------------------------------------------

# Lobby Server Name
server: dr-lobby-1

# Minimum amount of players that is needed to game start.
arena-min-players: 5

# Amount of players with 'DEATH' role on arena.
arena-deaths-amount: 1

# Amount of time that runners have to complete run. (in seconds)
arena-game-time: 600

# Amount of time that is needed to game start.
arena-pre-starting-time: 30

# Amount of time before start barrier will be removed.
arena-starting-time: 10

# Amount of time before players on server will be moved to lobby.
arena-end-delay: 10

# Amount of time before trap can be used again.
arena-trap-delay: 20

# Amount of time before strafe can be used again.
arena-strafe-delay: 30

# ------------------------------------------------------------------------
#                                 EFFECTS
# ------------------------------------------------------------------------

# Jump Boost Effect
effects-jump-boost-block: EMERALD_BLOCK
effects-jump-boost-amplifier: 5
effects-jump-boost-duration: 1.5

# Speed Effect
effects-speed-block: REDSTONE_BLOCK
effects-speed-amplifier: 5
effects-speed-duration: 1.5

# ------------------------------------------------------------------------
#                                SOUNDS
# ------------------------------------------------------------------------
arena-sound-pre-starting: BLOCK_NOTE_BLOCK_PLING
arena-sound-starting: ENTITY_EXPERIENCE_ORB_PICKUP
arena-sound-started: ENTITY_ENDER_DRAGON_GROWL
arena-sound-checkpoint-reached: ENTITY_EXPERIENCE_ORB_PICKUP
arena-sound-trap-delay: ENTITY_VILLAGER_NO
arena-sound-strafe-use: ENTITY_BLAZE_AMBIENT
arena-sound-player-death: ENTITY_SKELETON_DEATH
```

* Language File `(language.yml)`
```yaml
#  
# ------------------------------------------------------------------------
#                               INFORMATION
# ------------------------------------------------------------------------
#  
#  This is configuration file for DeathRun plugin, if you found any issue 
#  contact with us through Discord or create issue on GitHub. If you need
#  help with configuration visit https://mrstudios.pl/documentation.
#  

# ------------------------------------------------------------------------
#                                  GENERAL
# ------------------------------------------------------------------------
chat-message-no-permissions: <red>You don't have permissions to this command.
chat-message-arena-player-joined: <gray><player> <yellow>has joined. <aqua>(<currentPlayers>/<maxPlayers>)
chat-message-arena-player-left: <gray><player> <yellow>has quit.
chat-message-arena-starting-timer: <yellow>Game starts in <gold><timer> seconds<yellow>.
chat-message-arena-player-finished: '&r <white><b>FINISH > <gray>Player <gold><player>
  <gray>has finished game in <white><seconds> seconds<gray>. <dark_gray>(#<finishPosition>)'
chat-message-arena-game-start-runner:
- '&r'
- '&r   <gold><b>* <gray>You are <green>Runner<gray>.'
- '&r   <white><b>* <gray>Your task is complete run in shortest possible time, during
  this task interfering player will trigger various traps.'
- '&r'
chat-message-arena-game-start-death:
- '&r'
- '&r   <gold><b>* <gray>You are <red>Death<gray>.'
- '&r   <white><b>* <gray>Your task is to disturb runners by launching traps.'
- '&r'
chat-message-game-end-spectator:
- '&r'
- '&r   <gold><b>* <gray>You are <dark_gray>Spectator<gray>.'
- '&r   <white><b>* <gray>Now you can follow other players.'
- '&r'

# ------------------------------------------------------------------------
#                                  TITLES
# ------------------------------------------------------------------------
arena-pre-starting-title: <red><timer>
arena-pre-starting-subtitle: '&r'
arena-starting-title: <red><timer>
arena-starting-subtitle: '&r'
arena-death-title: <red>YOU DIED!
arena-death-subtitle: <yellow>Don't give up! Try again!
arena-checkpoint-title: <yellow>CHECKPOINT!
arena-checkpoint-subtitle: <gold>You reached <yellow>#<checkpoint> checkpoint<gold>.
arena-finish-title: <dark_aqua><b>FINISH
arena-finish-subtitle: <gray>Your position is <white>#<position><gray>.
arena-game-end-title: <red><b>GAME END!
arena-game-end-subtitle: '&r'
arena-move-server-title: <aqua>Waiting..
arena-move-server-subtitle: <gray>You will be transferred to lobby in <white><endTimer>
  seconds<gray>.

# ------------------------------------------------------------------------
#                               SCOREBOARD
# ------------------------------------------------------------------------
arena-scoreboard-title: <yellow><b>DEATH RUN
arena-scoreboard-lines-waiting:
- '&r'
- '<white>Map: <green><map>'
- '<white>Players: <green><currentPlayers>/<maxPlayers>'
- '&r'
- <white>Waiting..
- '&r'
- <yellow>www.mrstudios.pl
arena-scoreboard-lines-starting:
- '&r'
- '<white>Map: <green><map>'
- '<white>Players: <green><currentPlayers>/<maxPlayers>'
- '&r'
- <white>Start in <green><timer> seconds
- '&r'
- <yellow>www.mrstudios.pl
arena-scoreboard-lines-playing:
- '&r'
- '<white>Time: <green><time>'
- '<white>Role: <green><role>'
- '&r'
- '<white>Runners: <green><runners>'
- '<white>Deaths: <red><deaths>'
- '&r'
- '<white>Map: <green><map>'
- '&r'
- <yellow>www.mrstudios.pl

# ------------------------------------------------------------------------
#                               HOLOGRAMS
# ------------------------------------------------------------------------
arena-hologram-trap-delayed: <red><delay> seconds

# ------------------------------------------------------------------------
#                                 ROLES
# ------------------------------------------------------------------------
arena-roles-lobby-tab-prefix: <gray>

arena-roles-runner-name: <green>Runner
arena-roles-runner-tab-prefix: <gray>

arena-roles-death-name: <red>Death
arena-roles-death-tab-prefix: <gray>

arena-roles-spectator-name: <gray>Spectator
arena-roles-spectator-tab-prefix: <dark_gray>[SPECTATOR] <gray>

# ------------------------------------------------------------------------
#                                 ITEMS
# ------------------------------------------------------------------------
arena-item-teleport-name: <green>Teleport <gray>(Right Click)
arena-item-strafe-left-available-name: <green>Strafe Left <gray>(Right Click)
arena-item-strafe-left-unavailable-name: <green>Strafe Left <dark_gray>(<delay> seconds)
arena-item-strafe-back-available-name: <green>Strafe Back <gray>(Right Click)
arena-item-strafe-back-unavailable-name: <green>Strafe Back <dark_gray>(<delay> seconds)
arena-item-strafe-right-available-name: <green>Strafe Right <gray>(Right Click)
arena-item-strafe-right-unavailable-name: <green>Strafe Right <dark_gray>(<delay>
  seconds)
arena-item-leave-name: <red>Leave <gray>(Right Click)
```

