package pl.mrstudios.deathrun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.arena.tasks.ArenaMainTask;
import pl.mrstudios.deathrun.utils.MessageUtil;

public class CommandStart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can't use this command in console.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("mrstudios.command.deathrun.start")) {
            new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getNoPermissions()).send(player);
            return false;
        }

        if (!Main.instance().getArena().getGameState().equals(GameState.STARTING) || Main.instance().getArena().isStartShorted()) {
            new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getCommandStartCantUsageAtThisMoment()).send(player);
            return false;
        }

        ArenaMainTask.getInstance().setStartTimer(ArenaMainTask.getInstance().getStartTimer() / 2);
        Main.instance().getArena().setStartShorted(true);

        for (User target : Main.instance().getArena().getPlayers())
            new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getStartShorted()
                    .replace("<startTimer>", String.valueOf(ArenaMainTask.getInstance().getStartTimer()))
                    .replace("<player>", player.getName())
            ).send(target.getPlayer());

        return false;

    }

}
