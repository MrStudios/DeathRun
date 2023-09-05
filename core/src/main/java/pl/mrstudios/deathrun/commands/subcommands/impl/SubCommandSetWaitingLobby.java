package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;

import java.util.Collections;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandSetWaitingLobby extends SubCommand {

    public SubCommandSetWaitingLobby() {
        super("setwaitinglobby");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        Configuration.MAP.ARENA_WAITING_LOBBY_LOCATION = new Location(
                player.getWorld(),
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ(),
                player.getLocation().getPitch(),
                player.getLocation().getYaw()
        ).add(0.5, 0, 0.5);
        player.sendMessage(API.INSTANCE.VERSION.serialize("&r &2&l» &aArena waiting lobby has been set."));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {
        return Collections.emptyList();
    }

}
