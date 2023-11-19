package pl.mrstudios.deathrun.api.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.user.IUser;

import java.util.List;
import java.util.UUID;

public interface IArena {

    /* Arena Data */
    String getName();

    GameState getGameState();
    void setGameState(@NotNull GameState gameState);

    /* Arena User */
    List<IUser> getUsers();

    @Nullable IUser getUser(@NotNull String string);
    @Nullable IUser getUser(@NotNull UUID uniqueId);
    @Nullable IUser getUser(@NotNull Player player);

}
