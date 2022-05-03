package pl.mrstudios.deathrun.arena.objects;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.tasks.ArenaMainTask;
import pl.mrstudios.deathrun.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Scoreboard {

    private final Player player;
    private final Objective objective;
    private final org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    public Scoreboard(Player player, String title, List<String> lines) {

        this.player = player;
        this.objective = scoreboard.registerNewObjective("sidebar", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(new MessageUtil(title).color().getMessage());

        List<String> fixedList = new ArrayList<>();
        User user = Main.instance().getArena().getUser(player);

        for (String string : lines)
            fixedList.add(string
                    .replace("<mapName>", Main.instance().getConfiguration().getMapConfig().getName())
                    .replace("<currentPlayers>", String.valueOf(Main.instance().getArena().getPlayers().size()))
                    .replace("<maxPlayers>", String.valueOf(Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxPlayers()))
                    .replace("<startTimer>", String.valueOf(ArenaMainTask.getInstance().getStartTimer()))
                    .replace("<deathsAmount>", String.valueOf(user.getDeaths()))
                    .replace("<runnersLeft>", String.valueOf(Main.instance().getArena().getRunners().size()))
                    .replace("<playerRole>", user.getRole().getDisplayName())
                    .replace("<timeLeft>", ArenaMainTask.getInstance().getTimeLeft())
            );

        this.setLines(new MessageUtil(fixedList).color().getMessages());

        this.player.setScoreboard(this.scoreboard);

    }

    public void setLines(List<String> lines) {

        for (Team team : this.scoreboard.getTeams())
            team.unregister();

        byte b = 0;
        for (int i = lines.size(); i > 0; i--) {

            Team team = this.scoreboard.registerNewTeam("line_" + i);

            setLine(team, lines.get(i - 1));
            this.objective.getScore(ChatColor.values()[i - 1].toString() + team.getPrefix() + team.getSuffix()).setScore(b);
            b++;

        }

    }

    public void setLine(Team team, String string) {

        if (string.length() >= 16) {

            String str1, str2 = string.substring(0, 16);

            if (str2.endsWith("&") || str2.endsWith("§")) {

                str2 = str2.substring(0, str2.length() - 1);
                str1 = string.substring(str2.length());

            } else if (str2.substring(0, 15).endsWith("&") || str2.substring(0, 15).endsWith("§")) {

                str2 = str2.substring(0, str2.length() - 2);
                str1 = string.substring(str2.length());

            } else str1 = ChatColor.getLastColors(str2) + string.substring(str2.length());

            if (str1.length() > 16) str1 = string.substring(0, 16);

            team.setPrefix(str2);
            team.setSuffix(str1);

        } else {

            team.setPrefix(string);
            team.setSuffix("");

        }

    }


}