package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;

import java.util.Collections;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandHelp extends SubCommand {

    public SubCommandHelp() {
        super("help");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        List.of(

                "&r",
                "&r &6&lDeath Run &e&l» &7Commands:",
                "&r",
                "&r &f&l* &a/deathrun setname <name>",
                "&r &f&l* &a/deathrun setwaitinglobby",
                "&r &f&l* &a/deathrun setstartbarrier (material)",
                "&r &f&l* &a/deathrun addspawn <runner/death>",
                "&r &f&l* &a/deathrun addtrap <type> (material)",
                "&r &f&l* &a/deathrun addcheckpoint",
                "&r &f&l* &a/deathrun save",
                "&r"

        ).forEach(
                (element) -> player.sendMessage(API.INSTANCE.VERSION.serialize(element))
        );

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
