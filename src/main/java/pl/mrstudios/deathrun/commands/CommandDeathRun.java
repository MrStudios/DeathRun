package pl.mrstudios.deathrun.commands;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.*;
import pl.mrstudios.deathrun.arena.tasks.ArenaMainTask;
import pl.mrstudios.deathrun.events.WandClickListener;
import pl.mrstudios.deathrun.utils.ChannelUtil;
import pl.mrstudios.deathrun.utils.ItemUtil;
import pl.mrstudios.deathrun.utils.MessageUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Getter @Setter
public class CommandDeathRun implements TabExecutor {

    private StringBuilder traps = new StringBuilder();

    public CommandDeathRun() {

        traps.append("&r");

        for (int i = 0; i < 3; i++)
            traps.append(" ");

        for (int i = 0; i < Trap.Type.values().length; i++)
            if (i == (Trap.Type.values().length - 1))
                this.traps.append("&r &7and &f").append(Trap.Type.values()[i]).append("&7.");
            else if (i == 0)
                this.traps.append("&f").append(Trap.Type.values()[i]);
            else
                this.traps.append("&7, &f").append(Trap.Type.values()[i]);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can't use this command in console.");
            return false;
        }

        Player player = (Player) sender;
        Location location = new Location(player.getLocation().getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), player.getLocation().getPitch(), player.getLocation().getYaw());

        if (Main.instance().getConfiguration().getMapConfig().isSetup()) {

            if (!player.hasPermission("mrstudios.command.deathrun")) {
                new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getNoPermissions()).send(player);
                return false;
            }

            try {

                switch (args[0].toLowerCase()) {

                    case "setname": {

                        try {

                            Main.instance().getConfiguration().getMapConfig().setName(args[1]);
                            SoundUtil.playSound(player, "LEVEL_UP");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aArena name has been set successfully. &2&l*").color().getMessage());

                        } catch (Exception exception) {

                            new MessageUtil("&r &c&l» &7Correct usage is &f/dr setname <name>&7.").send(player);

                            SoundUtil.playSound(player, "ANVIL_BREAK");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cInvalid command usage! &4&l*").color().getMessage());

                        }

                        break;

                    }

                    case "setlobby": {

                        Main.instance().getConfiguration().getMapConfig().setWaitingLobby(location);
                        SoundUtil.playSound(player, "LEVEL_UP");
                        player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aArena lobby has been set successfully. &2&l*").color().getMessage());
                        break;

                    }

                    case "setstartglass": {

                        Main.instance().getConfiguration().getMapConfig().setStartGlass(WandClickListener.instance().getLocations());
                        SoundUtil.playSound(player, "LEVEL_UP");
                        player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aArena start glass has been set successfully. &2&l*").color().getMessage());
                        break;

                    }

                    case "addspawn": {

                        try {

                            switch (args[1].toLowerCase()) {

                                case "runner": {

                                    if (Objects.isNull(Main.instance().getConfiguration().getMapConfig().getRunnerSpawns()))
                                        Main.instance().getConfiguration().getMapConfig().setRunnerSpawns(new ArrayList<>());

                                    Main.instance().getConfiguration().getMapConfig().getRunnerSpawns().add(location);

                                    SoundUtil.playSound(player, "LEVEL_UP");
                                    player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aAdded runner spawn. &2&l*").color().getMessage());
                                    break;

                                }

                                case "death": {

                                    if (Objects.isNull(Main.instance().getConfiguration().getMapConfig().getDeathSpawns()))
                                        Main.instance().getConfiguration().getMapConfig().setDeathSpawns(new ArrayList<>());

                                    Main.instance().getConfiguration().getMapConfig().getDeathSpawns().add(location);

                                    SoundUtil.playSound(player, "LEVEL_UP");
                                    player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aAdded death spawn. &2&l*").color().getMessage());
                                    break;

                                }

                                default: {

                                    SoundUtil.playSound(player, "ANVIL_BREAK");
                                    player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cInvalid role provided! &4&l*").color().getMessage());
                                    break;

                                }

                            }

                        } catch (Exception exception) {

                            new MessageUtil("&r &c&l» &7Correct usage is &f/dr addspawn <runner/death>&7.").send(player);

                            SoundUtil.playSound(player, "ANVIL_BREAK");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cInvalid command usage! &4&l*").color().getMessage());

                        }

                        break;

                    }

                    case "addtrap": {

                        try {

                            if (Objects.isNull(Main.instance().getConfiguration().getMapConfig().getTraps()))
                                Main.instance().getConfiguration().getMapConfig().setTraps(new ArrayList<>());

                            Block block = player.getTargetBlock((Set<Material>) null, 250);
                            Location targetBlockLocation = new Location(block.getWorld().getName(), block.getX(), block.getY(), block.getZ(), 0, 0);

                            if (!block.getType().equals(Material.STONE_BUTTON) && !block.getType().equals(Material.WOOD_BUTTON)) {

                                SoundUtil.playSound(player, "ANVIL_BREAK");
                                player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cYou must looking at button. &4&l*").color().getMessage());
                                return false;

                            }

                            Trap trap = new Trap(Main.instance().getConfiguration().getMapConfig().getTraps().size(), Trap.Type.valueOf(args[1]), targetBlockLocation, WandClickListener.instance().getLocations());
                            Main.instance().getConfiguration().getMapConfig().getTraps().add(trap);

                            SoundUtil.playSound(player, "LEVEL_UP");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aTrap added successfully. &2&l*").color().getMessage());


                        } catch (Exception exception) {

                            new MessageUtil("&r &c&l» &7Correct usage is &f/dr addtrap <type>&7.").send(player);

                            SoundUtil.playSound(player, "ANVIL_BREAK");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cInvalid command usage! &4&l*").color().getMessage());

                        }

                        break;

                    }

                    case "addcheckpoint": {

                        if (Objects.isNull(Main.instance().getConfiguration().getMapConfig().getCheckpoints()))
                            Main.instance().getConfiguration().getMapConfig().setCheckpoints(new ArrayList<>());

                        Main.instance().getConfiguration().getMapConfig().getCheckpoints().add(new Checkpoint(Main.instance().getConfiguration().getMapConfig().getCheckpoints().size(), location, WandClickListener.instance().getLocations()));

                        SoundUtil.playSound(player, "LEVEL_UP");
                        player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aCheckpoint added successfully. &2&l*").color().getMessage());
                        break;

                    }

                    case "wand": {

                        player.getInventory().setHeldItemSlot(0);
                        player.getInventory().setItem(0, new ItemUtil(Material.GOLD_AXE, 1)
                                .name("&8&l* &7Setup Wand &8&l*")
                                .lore(
                                        "&r",
                                        "&r &6&l* &7This item is used to mark point data",
                                        "&r   &7on the basis of which a block map will",
                                        "&r   &7be created.",
                                        "&r"
                                )
                                .itemFlag(
                                        ItemFlag.HIDE_ATTRIBUTES,
                                        ItemFlag.HIDE_ENCHANTS,
                                        ItemFlag.HIDE_UNBREAKABLE
                                )
                                .enchantment(Enchantment.DURABILITY, 10)
                                .unbreakable()
                                .build()

                        );

                        SoundUtil.playSound(player, "ITEM_PICKUP");
                        break;

                    }

                    case "save": {

                        player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&f&l* &7Saving arena configuration.. &f&l*").color().getMessage());
                        SoundUtil.playSound(player, "ORB_PICKUP");

                        if (Objects.isNull(Main.instance().getArena().getMapConfig().getName()) || Objects.isNull(Main.instance().getArena().getMapConfig().getWaitingLobby()) || Objects.isNull(Main.instance().getArena().getMapConfig().getCheckpoints()) || Main.instance().getArena().getMapConfig().getCheckpoints().size() <= 0 || Objects.isNull(Main.instance().getArena().getMapConfig().getRunnerSpawns()) || Objects.isNull(Main.instance().getArena().getMapConfig().getDeathSpawns()) || Objects.isNull(Main.instance().getArena().getMapConfig().getStartGlass())) {

                            SoundUtil.playSound(player, "ANVIL_BREAK");
                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cArena setup is incomplete! &4&l*").color().getMessage());
                            return false;
                        }

                        if (Main.instance().getArena().getMapConfig().getTraps() == null)
                            Main.instance().getArena().getMapConfig().setTraps(new ArrayList<>());

                        Main.instance().getConfiguration().getMapConfig().setSetup(false);

                        try {

                            Gson gson = new Gson();
                            File file = new File(Main.instance().getDataFolder(), "arena.json");

                            if (file.exists())
                                file.delete();

                            if (!file.exists())
                                file.createNewFile();

                            FileWriter fileWriter = new FileWriter(file);

                            fileWriter.write(gson.toJson(Main.instance().getConfiguration().getMapConfig()));
                            fileWriter.flush();

                            fileWriter.close();

                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&2&l* &aArena configuration saved! &2&l*").color().getMessage());
                            SoundUtil.playSound(player, "LEVEL_UP");

                            new DelayExecution(3) {

                                @Override
                                public void run() {

                                    SoundUtil.playSound(player, "BURP");
                                    player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&8&l* &7Server will restart in &f5 seconds&7. &8&l*").color().getMessage());

                                    new DelayExecution(5) {

                                        @Override
                                        public void run() {

                                            Bukkit.getServer().shutdown();

                                        }

                                    };

                                }

                            };

                        } catch (Exception exception) {

                            player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cCan't save arena configuration. &4&l*").color().getMessage());
                            SoundUtil.playSound(player, "ITEM_BREAK");

                        }

                        break;

                    }

                    default: {

                        new MessageUtil(

                                "&r",
                                "&r &e&lDeath Run &8» &7Available Commands:",
                                "&r",
                                "&r &f&l* &a/dr setname <arena name>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getName()) ? " &8&o(required)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr setlobby" + (Objects.isNull(Main.instance().getArena().getMapConfig().getWaitingLobby()) ? " &8&o(required)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr setstartglass" + (Objects.isNull(Main.instance().getArena().getMapConfig().getStartGlass()) ? " &8&o(required)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr addspawn <runner/death>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getRunnerSpawns()) || Objects.isNull(Main.instance().getArena().getMapConfig().getDeathSpawns()) ? " &8&o(required)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr addtrap <type>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getTraps()) || Main.instance().getArena().getMapConfig().getTraps().size() <= 0 ? " &8&o(optional)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr addcheckpoint" + (Objects.isNull(Main.instance().getArena().getMapConfig().getCheckpoints()) || Main.instance().getArena().getMapConfig().getCheckpoints().size() <= 0 ? " &8&o(required)" : "&r &8&o(done)"),
                                "&r &f&l* &a/dr wand",
                                "&r &f&l* &a/dr save",
                                "&r",
                                "&r &6&l* &7Trap Types:",
                                this.traps.toString(),
                                "&r"

                        ).send(player);

                    }

                }

            } catch (Exception exception) {

                new MessageUtil(

                        "&r",
                        "&r &e&lDeath Run &8» &7Available Commands:",
                        "&r",
                        "&r &f&l* &a/dr setname <arena name>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getName()) ? " &8&o(required)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr setlobby" + (Objects.isNull(Main.instance().getArena().getMapConfig().getWaitingLobby()) ? " &8&o(required)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr setstartglass" + (Objects.isNull(Main.instance().getArena().getMapConfig().getStartGlass()) ? " &8&o(required)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr addspawn <runner/death>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getRunnerSpawns()) || Objects.isNull(Main.instance().getArena().getMapConfig().getDeathSpawns()) ? " &8&o(required)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr addtrap <type>" + (Objects.isNull(Main.instance().getArena().getMapConfig().getTraps()) || Main.instance().getArena().getMapConfig().getTraps().size() <= 0 ? " &8&o(optional)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr addcheckpoint" + (Objects.isNull(Main.instance().getArena().getMapConfig().getCheckpoints()) || Main.instance().getArena().getMapConfig().getCheckpoints().size() <= 0 ? " &8&o(required)" : "&r &8&o(done)"),
                        "&r &f&l* &a/dr wand",
                        "&r &f&l* &a/dr save",
                        "&r",
                        "&r &6&l* &7Trap Types:",
                        this.traps.toString(),
                        "&r"

                ).send(player);

                if (args.length != 0) {
                    SoundUtil.playSound(player, "ANVIL_BREAK");
                    player.sendTitle(new MessageUtil("&6&l* &f&lDeath Run &6&l*").color().getMessage(), new MessageUtil("&4&l* &cCommand isn't exists! &4&l*").color().getMessage());
                }

            }

            return false;

        }

        try {

            User user = Main.instance().getArena().getUser(player);

            switch (args[0].toLowerCase()) {

                case "start": {

                    if (!player.hasPermission("mrstudios.command.deathrun.start")) {
                        new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getNoPermissions()).send(player);
                        break;
                    }

                    if (!Main.instance().getArena().getGameState().equals(GameState.STARTING) && !Main.instance().getArena().isStartShorted()) {
                        new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getCommandStartCantUsageAtThisMoment()).send(player);
                        break;
                    }

                    Main.instance().getArena().setStartShorted(true);
                    ArenaMainTask.getInstance().setStartTimer(ArenaMainTask.getInstance().getStartTimer() / 2);

                    for (User target : Main.instance().getArena().getPlayers())
                        new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getStartShorted()
                                .replace("<startTimer>", String.valueOf(ArenaMainTask.getInstance().getStartTimer()))
                                .replace("<player>", user.getPlayer().getName())
                        ).send(target.getPlayer());

                    break;

                }

                case "leave": {
                    ChannelUtil.writeMessageAtChannel(user, "BungeeCord", "Connect", Main.instance().getConfiguration().getSettings().getServer().getLobbyServer());
                    break;
                }

                default: {

                    new MessageUtil(
                            "&r",
                            "&r   &cDeath Run &8(&b" + Main.instance().getDescription().getVersion() + "&8) &7created by &3SfenKer   &r",
                            "&r",
                            "&r   &6&l* &7Available Commands:",
                            "&r",
                            "&r     &f&l* &a/deathrun start",
                            "&r     &f&l* &a/deathrun leave",
                            "&r"
                    ).color().send(player);
                    break;
                }

            }

        } catch (Exception exception) {

            new MessageUtil(
                    "&r",
                    "&r   &cDeath Run &8(&b" + Main.instance().getDescription().getVersion() + "&8) &7created by &3SfenKer   &r",
                    "&r",
                    "&r   &6&l* &7Available Commands:",
                    "&r",
                    "&r     &f&l* &a/deathrun start",
                    "&r     &f&l* &a/deathrun leave",
                    "&r"
            ).color().send(player);

        }

        return false;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> strings = new ArrayList<>();

        if (Main.instance().getConfiguration().getMapConfig().isSetup())
            switch (args.length) {

                case 1: {

                    Arrays.asList(
                            "setname",
                            "setlobby",
                            "setstartglass",
                            "addspawn",
                            "addtrap",
                            "addcheckpoint",
                            "wand",
                            "save"
                    ).forEach(string -> {

                        if (string.startsWith(args[0].toLowerCase()))
                            strings.add(string);

                    });

                    break;

                }

                case 2: {

                    switch (args[0].toLowerCase()) {

                        case "addtrap": {

                            for (Trap.Type type : Trap.Type.values())
                                if (type.name().startsWith(args[1].toUpperCase()))
                                    strings.add(type.name());

                            break;

                        }

                        case "addspawn": {

                            Arrays.asList(
                                    "runner",
                                    "death"
                            ).forEach(string -> {

                                if (string.startsWith(args[1].toLowerCase()))
                                    strings.add(string);

                            });
                            break;

                        }

                        default:
                            break;

                    }

                    break;

                }

                default:
                    break;

            }
        else
            switch (args.length) {

                case 1: {

                    Arrays.asList(
                            "start",
                            "leave"
                    ).forEach(string -> {

                        if (string.startsWith(args[0].toLowerCase()))
                            strings.add(string);

                    });
                    break;

                }

                default:
                    break;

            }

        return strings;

    }

}
