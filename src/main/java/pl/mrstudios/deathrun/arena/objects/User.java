package pl.mrstudios.deathrun.arena.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.tasks.ArenaMainTask;
import pl.mrstudios.deathrun.utils.ItemUtil;
import pl.mrstudios.deathrun.utils.MessageUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

import java.util.Objects;

@Getter @Setter
public class User {

    private Player player;

    private int deaths;
    private int finishPosition;

    private Role role;
    private Checkpoint checkpoint;

    public User(Player player) {
        this.player = player;
        this.setRole(Role.UNKNOWN);
        this.deaths = 0;
        this.checkpoint = Main.instance().getConfiguration().getMapConfig().getCheckpoints().get(0);
    }

    public void setRole(Role role) {

        this.role = role;
        this.player.setPlayerListName(new MessageUtil(this.role.getTabListPrefix()).color().getMessage() + this.player.getName());

    }

    public void respawn() {

        player.sendTitle(
                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getDeathTitle()).color().getMessage(),
                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getDeathSubTitle()).color().getMessage()
        );

        if (!Objects.isNull(checkpoint))
            player.teleport(checkpoint.getSpawnLocation().bukkit());

        SoundUtil.playSound(this.player, Main.instance().getConfiguration().getSettings().getSounds().getArenaPlayerDeath());
        player.setFireTicks(0);
        this.deaths++;

    }

    public void finish() {

        this.setRole(Role.SPECTATOR);

        for (User user : Main.instance().getArena().getPlayers())
            if (!user.getPlayer().getName().equals(this.player.getName()))
                if (user.getRole().equals(Role.RUNNER) || user.getRole().equals(Role.DEATH)) {
                    user.getPlayer().hidePlayer(this.player);
                    this.player.showPlayer(user.getPlayer());
                } else {
                    user.getPlayer().showPlayer(this.player);
                    this.player.showPlayer(user.getPlayer());
                }

        this.player.getInventory().clear();
        this.player.getInventory().setItem(8, new ItemUtil(Material.BED, 1).name(Main.instance().getConfiguration().getMessages().getItems().getLeave()).itemFlag(ItemFlag.HIDE_ATTRIBUTES).build());

        this.player.setAllowFlight(true);
        this.player.teleport(Main.instance().getArena().getMapConfig().getCheckpoints().get(0).getSpawnLocation().bukkit());

        Main.instance().getArena().getRunners().remove(this);
        Main.instance().getArena().getSpectators().add(this);

        this.finishPosition = Main.instance().getArena().getSpectators().size();

        player.sendTitle(
                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getFinishTitle()).color().getMessage(),
                new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getFinishSubTitle().replace("<finishPosition>", String.valueOf(this.getFinishPosition()))).color().getMessage()
        );

        for (User user : Main.instance().getArena().getPlayers()) {

            new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getPlayerFinishedGame()
                    .replace("<finishTime>", ArenaMainTask.getInstance().getTimeElapsed())
                    .replace("<finishPosition>", String.valueOf(this.getFinishPosition()))
                    .replace("<player>", this.getPlayer().getName())
            ).color().send(user.getPlayer());

        }

        if (Main.instance().getArena().getSpectators().size() <= 1) {

            ArenaMainTask.getInstance().getLeftTime()[0] = 2;
            ArenaMainTask.getInstance().getLeftTime()[1] = 0;

        }

    }

}
