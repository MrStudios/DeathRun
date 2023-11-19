package pl.mrstudios.deathrun.arena.sidebar;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.sidebar.ISidebar;
import pl.mrstudios.deathrun.api.arena.sidebar.ISidebarLine;
import pl.mrstudios.deathrun.api.arena.user.IUser;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;

@Getter @Setter
public class Sidebar implements ISidebar {

    /* Viewer */
    private IUser viewer;

    /* Instance Data */
    private Component title;
    private Map<Team, Callable<Component>> lines;

    private Timer timer;
    private Objective objective;
    private Scoreboard scoreboard;

    public Sidebar() {
        this.timer = new Timer();
        this.lines = new HashMap<>();
    }

    @Override
    public ISidebar setViewer(IUser user) {
        this.viewer = user;
        return this;
    }

    @Override
    public ISidebar setTitle(Component component) {
        this.title = component;
        return this;
    }

    @Override
    public ISidebar create() {

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("sidebar", "dummy", this.title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        return this;

    }

    @Override
    public void update() {

        this.lines.keySet()
                .forEach((team) -> {
                    try {
                        team.prefix(this.lines.get(team).call());
                    } catch (Exception ignored) {}
                });

    }

    @Override
    public ISidebar setLines(@NotNull List<ISidebarLine> lines) {

        this.lines.clear();
        this.objective.unregister();
        this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        this.scoreboard.getTeams().forEach(Team::unregister);
        this.objective = this.scoreboard.registerNewObjective("sidebar", "dummy", this.title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        lines.forEach((line) -> {

            try {

                Team team = this.scoreboard.registerNewTeam("line:" + line.getScore());

                team.prefix(line.getContent().call());
                team.addEntry(ChatColor.values()[line.getScore()].toString());

                this.lines.put(team, line.getContent());
                this.objective.getScore(ChatColor.values()[line.getScore()].toString()).setScore(line.getScore());

            } catch (Exception ignored) {}

        });

        this.viewer.asBukkit().setScoreboard(this.scoreboard);
        return this;

    }

    @Override
    public void destroy() {
        this.timer.cancel();
        this.objective.unregister();
        this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        this.scoreboard.getTeams().forEach(Team::unregister);
    }

    @Override
    public ISidebar scheduleAutomaticUpdates(@NotNull Duration duration) {

        this.timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                update();
            }

        }, 0, duration.toMillis());
        return this;

    }

    @Override
    public ISidebar stopAutomaticUpdates() {
        this.timer.cancel();
        return this;
    }

}
