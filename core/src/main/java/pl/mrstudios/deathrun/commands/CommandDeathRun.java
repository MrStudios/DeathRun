package pl.mrstudios.deathrun.commands;

import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;
import pl.mrstudios.deathrun.data.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class CommandDeathRun implements TabExecutor {

    private final List<SubCommand> SUBCOMMANDS = new ArrayList<>();

    @SneakyThrows
    public CommandDeathRun() {

        new Reflections("pl.mrstudios.deathrun.commands.subcommands")
                .getClassesAnnotatedWith(RegistrableSubCommand.class)
                .forEach((subCommand) -> {
                    try {
                        this.SUBCOMMANDS.add((SubCommand) subCommand.getDeclaredConstructor().newInstance());
                    } catch (Exception ignored) {}
                });

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You cant use that command in this environment.");
            return false;
        }

        if (!Configuration.MAP.ARENA_SETUP_ENABLED) {
            player.sendMessage(API.INSTANCE.VERSION.serialize(
                    "&r &6&l» &eThis server is running on &6Death Run &eopensource plugin created by &6MrStudios Industries&e."
            ));
            return false;
        }

        if (!player.hasPermission("mrstudios.command.deathrun")) {
            player.sendMessage(API.INSTANCE.VERSION.serialize(Configuration.LANGUAGE.CHAT_MESSAGE_NO_PERMISSIONS));
            return false;
        }

        try {

            String[] arguments = new String[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);

            SUBCOMMANDS.stream()
                    .filter((subCommand) -> subCommand.getName().equalsIgnoreCase(args[0]))
                    .findAny()
                    .orElseThrow()
                    .onCommand(player, arguments);

        } catch (Exception exception) {
            player.sendMessage(API.INSTANCE.VERSION.serialize(
                    "&r &4&l» &cInvalid command usage, use &4/deathrun help &cfor command list."
            ));
        }

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        List<String> strings = new ArrayList<>();
        if (!(sender instanceof Player player))
            return strings;

        if (!Configuration.MAP.ARENA_SETUP_ENABLED)
            return strings;

        try {

            if (args.length == 1)
                this.SUBCOMMANDS.stream()
                        .filter((subCommand) -> subCommand.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                        .forEach((subCommand) -> strings.add(subCommand.getName()));

            if (args.length == 1)
                return strings;

            String[] arguments = new String[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);

            return this.SUBCOMMANDS.stream()
                    .filter((subCommand) -> subCommand.getName().equalsIgnoreCase(args[0]))
                    .findAny()
                    .orElseThrow()
                    .onTabCompletion(player, arguments);

        } catch (Exception ignored) {}

        return strings;

    }

}
