package pl.mrstudios.deathrun.config.list;

import lombok.Getter;
import lombok.Setter;
import pl.mrstudios.deathrun.config.Configuration;

import java.util.List;

@Getter @Setter
public class SettingsConfig extends Configuration.Adapter {

    /* Configuration Variables */
    private Settings settings;
    private Messages messages;

    @Getter
    @Setter
    public static class Settings {

        private License license;
        private Server server;
        private Arena arena;
        private MySQL mySQL;
        private Sounds sounds;

        @Getter @Setter
        public static class License {

            private String key;

        }

        @Getter @Setter
        public static class Server {

            private String thisServer;
            private String lobbyServer;

        }

        @Getter @Setter
        public static class Arena {

            private Limits limits;
            private Timers timers;
            private Effects effects;

            @Getter @Setter
            public static class Limits {

                private int minPlayers;
                private int maxPlayers;
                private int maxDeathPlayers;

            }

            @Getter @Setter
            public static class Timers {

                private int arenaInGameTime;
                private int arenaStartingTime;
                private int arenaEndMoveServerDelay;
                private int arenaTrapDelay;
                private int arenaStrafeDelay;

            }

            @Getter @Setter
            public static class Effects {

                private int jumpBoostAmplifier;
                private float jumpBoostTime;
                private int speedAmplifier;
                private float speedTime;

            }

        }

        @Getter @Setter
        public static class MySQL {

            private boolean enabled;
            private String host;
            private int port;
            private String name;
            private String user;
            private String password;

        }

        @Getter @Setter
        public static class Sounds {

            private String arenaStarting;
            private String arenaStartGlass;
            private String arenaStartGlassRemoved;
            private String arenaCheckpointReached;
            private String arenaTrapDelayEvent;
            private String arenaPlayerStrafeUse;
            private String arenaPlayerDeath;

        }

    }

    @Getter @Setter
    public static class Messages {

        private Chat chat;
        private Title title;
        private Scoreboard scoreboard;
        private Items items;
        private Holograms holograms;
        private Roles roles;
        private Kick kick;

        @Getter @Setter
        public static class Chat {

            private String noPermissions;
            private String playerJoined;
            private String playerLeft;
            private String startingTimer;
            private String playerFinishedGame;
            private String commandStartCantUsageAtThisMoment;
            private String startShorted;
            private List<String> gameStartRunner;
            private List<String> gameStartDeath;
            private List<String> gameEndSpectator;

        }

        @Getter @Setter
        public static class Title {

            private String startingTitle;
            private String startingSubTitle;
            private String deathTitle;
            private String deathSubTitle;
            private String finishTitle;
            private String finishSubTitle;
            private String gameEndTitle;
            private String gameEndSubTitle;
            private String checkpointTitle;
            private String checkpointSubTitle;
            private String endMoveServerTitle;
            private String endMoveServerSubTitle;

        }

        @Getter @Setter
        public static class Scoreboard {

            private String title;

            private List<String> waiting;
            private List<String> starting;
            private List<String> playing;

        }

        @Getter @Setter
        public static class Items {

            private String teleport;
            private String strafeLeftAvailable;
            private String strafeBackAvailable;
            private String strafeRightAvailable;
            private String strafeLeftDelay;
            private String strafeBackDelay;
            private String strafeRightDelay;
            private String leave;

        }

        @Getter @Setter
        public static class Holograms {

            private String trapDelayHologram;

        }

        @Getter @Setter
        public static class Roles {

            private Lobby lobby;
            private Runner runner;
            private Death death;
            private Spectator spectator;

            @Getter @Setter
            public static class Lobby {

                private String tabListPrefix;

            }

            @Getter @Setter
            public static class Runner {

                private String displayName;
                private String tabListPrefix;

            }

            @Getter @Setter
            public static class Death {

                private String displayName;
                private String tabListPrefix;

            }

            @Getter @Setter
            public static class Spectator {

                private String displayName;
                private String tabListPrefix;

            }

        }

        @Getter @Setter
        public static class Kick {

            private String serverIsFull;
            private String gameInProgress;

        }

    }

    private int configVersion;

}
