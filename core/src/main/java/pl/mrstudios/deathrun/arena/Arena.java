package pl.mrstudios.deathrun.arena;

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

public class Arena implements IArena {

    /* Arena Data */
    private final String name;
    private Sidebar<Component> sidebar;

    private int elapsedTime;
    private int finishedRuns;
    private int remainingTime;

    private final List<IUser> users;
    private GameState gameState;

    public Arena(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.gameState = GameState.WAITING;
        this.elapsedTime = 0;
        this.finishedRuns = 0;
        this.remainingTime = 0;
    }

    public void setSidebar(@NotNull Sidebar<Component> sidebar) {
        this.sidebar = sidebar;
    }

    public int getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getFinishedRuns() {
        return this.finishedRuns;
    }

    public void setFinishedRuns(int finishedRuns) {
        this.finishedRuns = finishedRuns;
    }

    public int getRemainingTime() {
        return this.remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull Sidebar<Component> getSidebar() {
        return this.sidebar;
    }

    @Override
    public @NotNull GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void setGameState(@NotNull GameState gameState) {
        this.gameState = gameState;
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
    public @NotNull List<IUser> getUsers() {
        return this.users;
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
