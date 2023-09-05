<!-- HEADER INFO -->
## IMPORTANT!
This is version under development, if you want stable version use [version/0.4-alpha](https://github.com/MrStudios/DeathRun/tree/version/0.4-alpha).

<!-- WHAT IS THIS? -->
## WHAT IS THIS?
**Death Run** is a minigame that first appeared on **HiveMC Network** and become popular instantly. In this game idea is that as death we have to make the game difficult for runners who will try to reach finish line in shortest time possible.

<!-- PROGRESS -->
## PROGRESS
- [ ] Game System
- [ ] Development API *(70%)*
- [x] Arena Setup
- [x] Common Version Support (1.16+)
- [ ] Legacy Version Support (<1.16)
- [ ] Game Backend Server/Client (Velocity + Netty Application)
- [ ] Extensions
- - [ ] Private Games
- - [ ] Map Voting
- - [ ] Statistics

<!-- CONFIGURATION FILES -->
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
lobby_server_name: dr-lobby-1

# Minimum amount of players that is needed to game start.
arena_min_players: 5

# Amount of players with 'DEATH' role on arena.
arena_deaths_amount: 1

# Amount of time that runners have to complete run. (in seconds)
arena_game_time: 600

# Amount of time that is needed to game start.
arena_pre_starting_time: 30

# Amount of time before start barrier will be removed.
arena_starting_time: 10

# Amount of time before players on server will be moved to lobby.
arena_end_delay: 10

# Amount of time before trap can be used again.
arena_trap_delay: 20

# Amount of time before strafe can be used again.
arena_strafe_delay: 30

# ------------------------------------------------------------------------
#                                 EFFECTS
# ------------------------------------------------------------------------

# Jump Boost Effect
effects_jump_boost_block: EMERALD_BLOCK
effects_jump_boost_amplifier: 5
effects_jump_boost_duration: 1.5

# Speed Effect
effects_speed_block: REDSTONE_BLOCK
effects_speed_amplifier: 5
effects_speed_duration: 1.5

# ------------------------------------------------------------------------
#                                SOUNDS
# ------------------------------------------------------------------------
arena_sound_pre_starting: BLOCK_NOTE_BLOCK_PLING
arena_sound_starting: ENTITY_EXPERIENCE_ORB_PICKUP
arena_sound_started: ENTITY_ENDER_DRAGON_GROWL
arena_sound_checkpoint_reached: ENTITY_EXPERIENCE_ORB_PICKUP
arena_sound_trap_delay: ENTITY_VILLAGER_NO
arena_sound_strafe_use: ENTITY_BLAZE_AMBIENT
arena_sound_player_death: ENTITY_SKELETON_DEATH
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
chat_message_no_permissions: '&cYou don''t have permissions to this command.'
chat_message_arena_player_joined: '&7<player> &ehas joined. &b(<currentPlayers>/<maxPlayers>)'
chat_message_arena_player_left: '&7<player> &ehas quit.'
chat_message_arena_starting_timer: '&eGame starts in &6<timer> seconds&e.'
chat_message_arena_player_finished: '&r &f&lFINISH > &7Player &6<player> &7has finished
  game in &f<seconds> seconds&7. &8(#<finishPosition>)'
chat_message_arena_game_start_runner:
- '&r'
- '&r   &6&l* &7You are &aRunner&7.'
- '&r   &f&l* &7Your task is complete run in shortest possible time, during this task
  interfering player will trigger various traps.'
- '&r'
chat_message_arena_game_start_death:
- '&r'
- '&r   &6&l* &7You are &cDeath&7.'
- '&r   &f&l* &7Your task is to disturb runners by launching traps.'
- '&r'
chat_message_arena_game_end_spectator:
- '&r'
- '&r   &6&l* &7You are &8Spectator&7.'
- '&r   &f&l* &7Now you can follow other players.'
- '&r'

# ------------------------------------------------------------------------
#                                  TITLES
# ------------------------------------------------------------------------
arena_pre_starting_title: '&c<timer>'
arena_pre_starting_subtitle: '&r'
arena_starting_title: '&c<timer>'
arena_starting_subtitle: '&r'
arena_death_title: '&cYOU DIED!'
arena_death_subtitle: '&eDon''t give up! Try again!'
arena_checkpoint_title: '&eCHECKPOINT!'
arena_checkpoint_subtitle: '&6You reached &e#<checkpoint> checkpoint&6.'
arena_finish_title: '&3&lFINISH'
arena_finish_subtitle: '&7Your position is &f#<position>&7.'
arena_game_end_title: '&c&lGAME END!'
arena_game_end_subtitle: '&r'
arena_move_server_title: '&bWaiting..'
arena_move_server_subtitle: '&7You will be transferred to lobby in &f<endTimer> seconds&7.'

# ------------------------------------------------------------------------
#                               SCOREBOARD
# ------------------------------------------------------------------------
arena_scoreboard_title: '&e&lDEATH RUN'
arena_scoreboard_lines_waiting:
- '&r'
- '&fMap: &a<map>'
- '&fPlayers: &a<currentPlayers>/<maxPlayers>'
- '&r'
- '&fWaiting..'
- '&r'
- '&ewww.mrstudios.pl'
arena_scoreboard_lines_starting:
- '&r'
- '&fMap: &a<map>'
- '&fPlayers: &a<currentPlayers>/<maxPlayers>'
- '&r'
- '&fStart in &a<timer> seconds'
- '&r'
- '&ewww.mrstudios.pl'
arena_scoreboard_lines_playing:
- '&r'
- '&fTime: &a<time>'
- '&fRole: &a<role>'
- '&r'
- '&fRunners: &a<runners>'
- '&fDeaths: &c<deaths>'
- '&r'
- '&fMap: &a<map>'
- '&r'
- '&ewww.mrstudios.pl'

# ------------------------------------------------------------------------
#                               HOLOGRAMS
# ------------------------------------------------------------------------
arena_hologram_trap_delayed: '&c<delay> seconds'

# ------------------------------------------------------------------------
#                                 ROLES
# ------------------------------------------------------------------------
arena_roles_lobby_tab_prefix: '&7'

arena_roles_runner_name: '&aRunner'
arena_roles_runner_tab_prefix: '&7'

arena_roles_death_name: '&cDeath'
arena_roles_death_tab_prefix: '&7'

arena_roles_spectator_name: '&7Spectator'
arena_roles_spectator_tab_prefix: '&8[SPECTATOR] &7'

# ------------------------------------------------------------------------
#                                 ITEMS
# ------------------------------------------------------------------------
arena_item_teleport_name: '&aTeleport &7(Right Click)'
arena_item_strafe_left_available_name: '&aStrafe Left &7(Right Click)'
arena_item_strafe_left_unavailable_name: '&aStrafe Left &8(<delay> seconds)'
arena_item_strafe_back_available_name: '&aStrafe Back &7(Right Click)'
arena_item_strafe_back_unavailable_name: '&aStrafe Back &8(<delay> seconds)'
arena_item_strafe_right_available_name: '&aStrafe Right &7(Right Click)'
arena_item_strafe_right_unavailable_name: '&aStrafe Right &8(<delay> seconds)'
arena_item_leave_name: '&cLeave &7(Right Click)'
```

<!-- INFORMATION -->
## INFORMATION
Update will be finished in this or next month depending on my free time.

