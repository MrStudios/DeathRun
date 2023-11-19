package pl.mrstudios.deathrun.api.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.user.IUser;

import java.util.Collection;
import java.util.UUID;

public interface IArena {

    /* Arena Data */
    String getName();
    long getStartTime();

    int getFinishedRuns();
    void setFinishedRuns(int amount);

    GameState getGameState();
    void setGameState(@NotNull GameState gameState);

    /* Arena User */
    Collection<IUser> getUsers();
    Collection<IUser> getRunners();
    Collection<IUser> getDeaths();
    Collection<IUser> getSpectators();

    @Nullable IUser getUser(@NotNull String string);
    @Nullable IUser getUser(@NotNull UUID uniqueId);
    @Nullable IUser getUser(@NotNull Player player);

}
