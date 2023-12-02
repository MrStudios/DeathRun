package pl.mrstudios.deathrun.api.arena;

import me.catcoder.sidebar.Sidebar;
import net.kyori.adventure.text.Component;
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
    long getStartTime();

    int getFinishedRuns();
    void setFinishedRuns(int amount);

    GameState getGameState();
    void setGameState(@NotNull GameState gameState);

    /* Sidebar */
    Sidebar<Component> getSidebar();
    void setSidebar(@NotNull Sidebar<Component> sidebar);

    /* Arena User */
    List<IUser> getUsers();
    List<IUser> getRunners();
    List<IUser> getDeaths();
    List<IUser> getSpectators();

    @Nullable IUser getUser(@NotNull String string);
    @Nullable IUser getUser(@NotNull UUID uniqueId);
    @Nullable IUser getUser(@NotNull Player player);

}
