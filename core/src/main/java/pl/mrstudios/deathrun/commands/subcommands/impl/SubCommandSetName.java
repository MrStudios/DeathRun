package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;

import java.util.Collections;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandSetName extends SubCommand {

    public SubCommandSetName() {
        super("setname");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        Configuration.MAP.ARENA_NAME = args[0];
        player.sendMessage(API.INSTANCE.VERSION.serialize("&r &2&l» &aArena name has been set."));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {
        return Collections.singletonList("<name>");
    }

}
