package pl.mrstudios.deathrun.commands.subcommands.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.api.arena.enums.Role;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RegistrableSubCommand()
public class SubCommandAddSpawn extends SubCommand {

    public SubCommandAddSpawn() {
        super("addspawn");
    }

    @Override
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        Role role = Role.valueOf(args[0].toUpperCase());

        if (role.equals(Role.SPECTATOR) || role.equals(Role.UNKNOWN))
            throw new UnsupportedOperationException("Command 'addspawn' is not supporting '" + role.name() + "' as role.");

        Location location = new Location(
                player.getWorld(),
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ(),
                player.getLocation().getPitch(),
                player.getLocation().getYaw()
        ).add(0.5, 0, 0.5);

        switch (role) {

            case RUNNER ->
                    Configuration.MAP.ARENA_RUNNER_SPAWN_LOCATIONS.add(location);

            case DEATH ->
                    Configuration.MAP.ARENA_DEATH_SPAWN_LOCATIONS.add(location);

            default ->
                throw new UnsupportedOperationException("Command 'addspawn' is not supporting '" + role.name() + "' as role.");

        }

        player.sendMessage(API.INSTANCE.VERSION.serialize("&r &2&l» &aAdded &2" + role.name() + " &aspawn."));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {

        List<String> strings = new ArrayList<>();
        if (args.length != 1)
            return strings;

        Arrays.stream(Role.values())
                .filter((role) -> role != Role.SPECTATOR && role != Role.UNKNOWN)
                .filter((role) -> role.name().toUpperCase().startsWith(args[0].toUpperCase()))
                .forEach((role) -> strings.add(role.name()));

        return strings;

    }

}
