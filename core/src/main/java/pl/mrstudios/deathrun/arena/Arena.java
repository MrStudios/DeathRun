package pl.mrstudios.deathrun.arena;

import lombok.Getter;
import lombok.Setter;
import me.catcoder.sidebar.Sidebar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Arena implements IArena {

    /* Arena Data */
    private String name;
    private Sidebar<Component> sidebar;

    private int elapsedTime;
    private int finishedRuns;
    private int remainingTime;

    private List<IUser> users;
    private GameState gameState;

    public Arena(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.gameState = GameState.WAITING;
        this.elapsedTime = 0;
        this.finishedRuns = 0;
        this.remainingTime = 0;
    }

    @Override
    public @Nullable IUser getUser(@NotNull String string) {
        return this.users.stream()
                .filter((user) -> user.getName().equals(string))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable IUser getUser(@NotNull UUID uniqueId) {
        return this.users.stream()
                .filter(
                        (user) -> user.getUniqueId().equals(uniqueId)
                ).findFirst()
                .orElse(null);
    }

    @Override
    public @Nullable IUser getUser(@NotNull Player player) {
        return this.users.stream()
                .filter((user) -> user.getName().equals(player.getName()) && user.getUniqueId().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public @NotNull List<IUser> getRunners() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.RUNNER))
                .toList();
    }

    @Override
    public @NotNull List<IUser> getDeaths() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.DEATH))
                .toList();
    }

    @Override
    public @NotNull List<IUser> getSpectators() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.SPECTATOR))
                .toList();
    }

}
