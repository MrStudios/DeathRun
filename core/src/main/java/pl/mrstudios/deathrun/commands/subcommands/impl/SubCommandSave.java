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
public class SubCommandSave extends SubCommand {

    public SubCommandSave() {
        super("save");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        Configuration.MAP.ARENA_SETUP_ENABLED = false;
        Configuration.MAP.save();

        player.sendMessage(API.INSTANCE.VERSION.serialize(
                "&r &2&l» &aArena configuration saved successfully, please restart server to apply changes."
        ));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
