package pl.mrstudios.deathrun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.utils.ChannelUtil;

public class CommandLeave implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(ChatColor.RED + "This command isn't available in console.");
            return false;

        }

        if (Main.instance().getConfiguration().getMapConfig().isSetup()) {
            sender.sendMessage(ChatColor.RED + "This command isn't available during setup.");
            return false;
        }

        User user = Main.instance().getArena().getUser((Player) sender);

        ChannelUtil.writeMessageAtChannel(user, "BungeeCord", "Connect", Main.instance().getConfiguration().getSettings().getServer().getLobbyServer());
        return false;

    }

}
