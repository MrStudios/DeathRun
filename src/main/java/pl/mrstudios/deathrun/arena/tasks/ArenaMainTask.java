package pl.mrstudios.deathrun.arena.tasks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.*;
import pl.mrstudios.deathrun.utils.ChannelUtil;
import pl.mrstudios.deathrun.utils.ItemUtil;
import pl.mrstudios.deathrun.utils.MessageUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.util.ArrayList;

@Getter @Setter
public class ArenaMainTask extends BukkitRunnable {

    @Getter @Setter
    private static ArenaMainTask instance;

    private int startTimer,
        endTimer;

    private boolean taskCreated,
        glassRemoved;

    private int[] leftTime = new int[] { 0, Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaInGameTime() },
                    elapsedTime = new int[] { 0, 0 };

    public ArenaMainTask() {

        setInstance(this);

        this.startTimer = Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaStartingTime();
        this.endTimer = Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaEndMoveServerDelay();
        this.taskCreated = false;
        this.glassRemoved = false;

        while (leftTime[1] >= 60) {
            leftTime[1] = leftTime[1] - 60;
            leftTime[0]++;
        }

    }

    @Override
    public void run() {

        switch (Main.instance().getArena().getGameState()) {

            case WAITING: {

                if (Main.instance().getArena().getPlayers().size() >= Main.instance().getConfiguration().getSettings().getArena().getLimits().getMinPlayers())
                    Main.instance().getArena().setGameState(GameState.STARTING);

                for (User user : Main.instance().getArena().getPlayers())
                    new Scoreboard(user.getPlayer(), Main.instance().getConfiguration().getMessages().getScoreboard().getTitle(), Main.instance().getConfiguration().getMessages().getScoreboard().getWaiting());

                break;

            }

            case STARTING: {

                if (Main.instance().getArena().getPlayers().size() < Main.instance().getConfiguration().getSettings().getArena().getLimits().getMinPlayers()) {

                    Main.instance().getArena().setGameState(GameState.WAITING);

                    this.startTimer = Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaStartingTime();

                    for (User user : Main.instance().getArena().getPlayers())
                        new Scoreboard(user.getPlayer(), Main.instance().getConfiguration().getMessages().getScoreboard().getTitle(), Main.instance().getConfiguration().getMessages().getScoreboard().getWaiting());

                    Main.instance().getArena().setStartShorted(false);
                    break;

                }

                for (User user : Main.instance().getArena().getPlayers()) {

                    new Scoreboard(user.getPlayer(), Main.instance().getConfiguration().getMessages().getScoreboard().getTitle(), Main.instance().getConfiguration().getMessages().getScoreboard().getStarting());

                    switch (startTimer) {

                        case 30:
                        case 20:
                        case 10:
                        case 5:
                        case 4:
                        case 3:
                        case 2:
                        case 1: {

                            new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getStartingTimer().replace("<startTimer>", String.valueOf(startTimer))).color().send(user.getPlayer());

                            SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaStarting());
                            user.getPlayer().sendTitle(
                                    new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getStartingTitle().replace("<startTimer>", String.valueOf(startTimer))).color().getMessage(),
                                    new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getStartingSubTitle().replace("<startTimer>", String.valueOf(startTimer))).color().getMessage()
                            );

                            break;

                        }

                        case 0: {

                            int runnerPos = 0,
                                    deathPos = 0;

                            for (User target : Main.instance().getArena().getPlayers()) {

                                target.getPlayer().getInventory().clear();

                                target.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
                                target.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1));

                                if (deathPos < Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxDeathPlayers()) {

                                    target.setRole(Role.DEATH);
                                    target.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                                    new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getGameStartDeath()).color().send(target.getPlayer());


                                    Main.instance().getArena().getDeaths().add(target);

                                    target.getPlayer().teleport(Main.instance().getArena().getMapConfig().getDeathSpawns().get(deathPos).bukkit());

                                    Main.instance().getArena().setRunners(new ArrayList<>());

                                    deathPos++;
                                    continue;

                                }

                                target.setRole(Role.RUNNER);
                                new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getGameStartRunner()).color().send(target.getPlayer());

                                Main.instance().getArena().getRunners().add(target);
                                target.getPlayer().teleport(Main.instance().getArena().getMapConfig().getRunnerSpawns().get(runnerPos).bukkit());

                                for (Strafe strafe : Strafe.getStrafes().values())
                                    target.getPlayer().getInventory().setItem(strafe.getType().getSlotId(), new ItemUtil(Material.SKULL_ITEM, 1, (byte) 3).name(strafe.getType().getAvailableStrafeName()).lore(strafe.getType().name()).headTexture(strafe.getType().getAvailableStrafeTexture()).itemFlag(ItemFlag.HIDE_ATTRIBUTES).build());

                                runnerPos++;

                            }

                            Main.instance().getArena().setGameState(GameState.PLAYING);
                            this.startTimer = 11;
                            break;

                        }

                        default:
                            break;

                    }

                }

                startTimer--;
                break;

            }

            case PLAYING: {

                boolean isTimeLeft = leftTime[0] == 0 && leftTime[1] == 0;
                if (Main.instance().getArena().getRunners().size() <= 0 || isTimeLeft) {

                    Main.instance().getArena().setGameState(GameState.ENDING);
                    break;

                }

                if (glassRemoved) {

                    if (leftTime[1] <= 0)
                        if (leftTime[0] > 0) {
                            leftTime[1] = 59;
                            leftTime[0]--;
                        } else
                            leftTime[1]--;
                    else
                        leftTime[1]--;

                    if (elapsedTime[1] >= 59) {
                        elapsedTime[0]++;
                        elapsedTime[1] = 0;
                    } else
                        elapsedTime[1]++;

                } else if (startTimer >= 0) {

                    for (User user : Main.instance().getArena().getRunners()) {

                        switch (startTimer) {

                            case 10:
                            case 5:
                            case 4:
                            case 3:
                            case 2:
                            case 1: {

                                SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaStartGlass());
                                user.getPlayer().sendTitle(
                                        new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getStartingTitle().replace("<startTimer>", String.valueOf(startTimer))).color().getMessage(),
                                        new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getStartingSubTitle().replace("<startTimer>", String.valueOf(startTimer))).color().getMessage()
                                );

                                break;

                            }

                            case 0: {

                                for (Location location : Main.instance().getArena().getMapConfig().getStartGlass())
                                    location.bukkit().getBlock().setType(Material.AIR);

                                SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaStartGlassRemoved());
                                this.glassRemoved = true;
                                break;
                            }

                            default:
                                break;

                        }

                    }

                    startTimer--;

                }

                for (User user : Main.instance().getArena().getPlayers()) {

                    if (user.getPlayer().isOnline())
                        new Scoreboard(user.getPlayer(), Main.instance().getConfiguration().getMessages().getScoreboard().getTitle(), Main.instance().getConfiguration().getMessages().getScoreboard().getPlaying());
                    else {
                        Main.instance().getArena().getPlayers().remove(user);
                        Main.instance().getArena().getDeaths().remove(user);
                        Main.instance().getArena().getSpectators().remove(user);
                        Main.instance().getArena().getRunners().remove(user);
                    }

                }

                break;

            }

            case ENDING: {

                if (endTimer <= -5)
                    Main.instance().getServer().shutdown();

                if (endTimer == Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaEndMoveServerDelay())
                    for (Player target : Main.instance().getServer().getOnlinePlayers())
                        target.sendTitle(
                                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getGameEndTitle()).color().getMessage(),
                                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getGameEndSubTitle()).color().getMessage()
                        );

                if (endTimer <= (Main.instance().getConfiguration().getSettings().getArena().getTimers().getArenaEndMoveServerDelay() - 5) && endTimer > 0)
                    for (Player target : Main.instance().getServer().getOnlinePlayers())
                        target.sendTitle(
                                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getEndMoveServerTitle()).color().getMessage(),
                                new MessageUtil(
                                        Main.instance().getConfiguration().getMessages().getTitle().getEndMoveServerSubTitle()
                                        .replace("<endTimer>", String.valueOf(endTimer))
                                ).color().getMessage()
                        );

                if (!taskCreated) {

                    setTaskCreated(true);
                    new DelayExecution(this.endTimer) {

                        @Override
                        public void run() {

                            for (Player player : Main.instance().getServer().getOnlinePlayers())
                                ChannelUtil.writeMessageAtChannel(Main.instance().getArena().getUser(player), "BungeeCord", "Connect", Main.instance().getConfiguration().getSettings().getServer().getLobbyServer());

                        }

                    };

                }

                endTimer--;
                break;

            }

            default:
                break;

        }

    }

    public String getTimeLeft() {

        StringBuilder stringBuilder = new StringBuilder();

        if (leftTime[0] >= 10)
            stringBuilder.append(leftTime[0]);
        else
            stringBuilder.append(0).append(leftTime[0]);

        stringBuilder.append(":");

        if (leftTime[1] >= 10)
            stringBuilder.append(leftTime[1]);
        else
            stringBuilder.append(0).append(leftTime[1]);

        return stringBuilder.toString();

    }

    public String getTimeElapsed() {

        StringBuilder stringBuilder = new StringBuilder();

        if (elapsedTime[0] >= 10)
            stringBuilder.append(elapsedTime[0]);
        else
            stringBuilder.append(0).append(elapsedTime[0]);

        stringBuilder.append(":");

        if (elapsedTime[1] >= 10)
            stringBuilder.append(elapsedTime[1]);
        else
            stringBuilder.append(0).append(elapsedTime[1]);

        return stringBuilder.toString();

    }

}
