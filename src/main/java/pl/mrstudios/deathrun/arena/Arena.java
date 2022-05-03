package pl.mrstudios.deathrun.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.Trap;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.config.list.MapConfig;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Arena {

    private GameState gameState;
    private MapConfig mapConfig;

    private boolean startShorted;

    private List<User> deaths = new ArrayList<>();
    private List<User> runners = new ArrayList<>();
    private List<User> players = new ArrayList<>();
    private List<User> spectators = new ArrayList<>();

    public Arena() {

        this.gameState = GameState.WAITING;
        this.mapConfig = Main.instance().getConfiguration().getMapConfig();
        this.startShorted = false;

    }

    public User getUser(Player player) {

        for (User user : this.players)
            if (user.getPlayer().getName().equals(player.getName()))
                return user;

        return null;

    }

    public Trap getTrap(Location location) {

        for (Trap trap : mapConfig.getTraps())
            if (trap.getButtonLocation().getX() == location.getBlockX() && trap.getButtonLocation().getY() == location.getBlockY() && trap.getButtonLocation().getZ() == location.getBlockZ())
                return trap;

        return null;

    }

}
