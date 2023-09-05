package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.arena.data.checkpoint.Checkpoint;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;
import pl.mrstudios.deathrun.listener.WandClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandAddCheckpoint extends SubCommand {

    public SubCommandAddCheckpoint() {
        super("addcheckpoint");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        List<Location> locations = new ArrayList<>(WandClickListener.LOCATIONS); // TODO: system idioto odporny

        Configuration.MAP.ARENA_CHECKPOINTS.add(new Checkpoint(
                Configuration.MAP.ARENA_CHECKPOINTS.size(), locations
        ));

        player.sendMessage(API.INSTANCE.VERSION.serialize(
                "&r &2&l» &aAdded arena checkpoint."
        ));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
