package pl.mrstudios.deathrun.commands.subcommands.impl;

import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.API;
import pl.mrstudios.deathrun.api.arena.interfaces.ITrap;
import pl.mrstudios.deathrun.commands.subcommands.SubCommand;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.data.annotations.RegistrableSubCommand;
import pl.mrstudios.deathrun.listener.WandClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RegistrableSubCommand()
public class SubCommandAddTrap extends SubCommand {

    public SubCommandAddTrap() {
        super("addtrap");
    }

    @Override
    @SneakyThrows
    public void onCommand(@NotNull Player player, @NotNull String[] args) {

        Block buttonBlock = player.getTargetBlock(null, 250);
        List<Location> locations = new ArrayList<>(WandClickListener.LOCATIONS);

        Material material = (args.length == 2) ? Material.valueOf(args[1].toUpperCase()) : null;
        Class<? extends ITrap> trapClass = API.INSTANCE.TRAP_REGISTRY.getOrDefault(args[0].toUpperCase(), null);

        if (trapClass == null) {
            player.sendMessage(API.INSTANCE.VERSION.serialize("&r &4&l» &cTrap &4" + args[0].toUpperCase() + " &cis not exists."));
            return;
        }

        // TODO: cos z tym gownem zrobic
        if (Stream.of(
                Material.STONE_BUTTON,
                Material.OAK_BUTTON,
                Material.ACACIA_BUTTON,
                Material.BIRCH_BUTTON,
                Material.CRIMSON_BUTTON,
                Material.JUNGLE_BUTTON,
                Material.SPRUCE_BUTTON,
                Material.WARPED_BUTTON
        ).noneMatch((button) -> buttonBlock.getType().equals(button))) {
            player.sendMessage(API.INSTANCE.VERSION.serialize("&r &4&l» &cYou must look at button that is activating trap."));
            return;
        }

        ITrap trap = trapClass.getDeclaredConstructor().newInstance();
        if (material != null)
            locations.removeIf(
                    (location) -> !location.getBlock().getType().equals(material)
            );

        trap.setButton(buttonBlock.getLocation());
        trap.setLocations(locations);

        if (material != null)
            trap.setExtra(material);

        Configuration.MAP.ARENA_TRAPS.add(trap);
        player.sendMessage(API.INSTANCE.VERSION.serialize("&r &2&l» &aAdded &2" + trap.getId() + " &asuccessfully."));

    }

    @Override
    public List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args) {

        List<String> strings = new ArrayList<>();

        switch (args.length) {

            case 1 ->
                    API.INSTANCE.TRAP_REGISTRY.keySet()
                            .stream()
                            .filter((key) -> key.toUpperCase().startsWith(args[0].toUpperCase()))
                            .forEach(strings::add);

            case 2 ->
                    Arrays.stream(Material.values())
                            .filter((material) -> material.name().startsWith(args[1].toUpperCase()))
                            .forEach((material) -> strings.add(material.name()));

            default -> { return strings; }

        }

        return strings;

    }

}
