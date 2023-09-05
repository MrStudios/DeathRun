package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;
import pl.mrstudios.deathrun.listener.WandClickListener;

import java.util.ArrayList;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandSetStartBarrier extends SubCommand {

    public SubCommandSetStartBarrier() {
        super("setstartbarrier");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        List<Location> locations = new ArrayList<>(WandClickListener.LOCATIONS);
        Material material = (args.length == 1) ? Material.valueOf(args[0].toUpperCase()) : null;

        if (material != null)
            locations.removeIf(
                    (location) -> !location.getBlock().getType().equals(material)
            );

        Configuration.MAP.ARENA_START_BARRIER_BLOCKS = locations;
        player.sendMessage(API.INSTANCE.VERSION.serialize("&r &2&l» &aArena start barrier has been set. " + ((material != null) ? "&8&o(" + material.name() + ")" : "")));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {

        List<String> strings = new ArrayList<>();
        if (args.length != 1)
            return strings;

        for (Material material : Material.values())
            if (material.isBlock())
                if (material.name().startsWith(args[0].toUpperCase()))
                    strings.add(material.name());

        return strings;

    }

}
