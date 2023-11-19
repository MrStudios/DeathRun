package pl.mrstudios.deathrun.api.arena.user;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.api.arena.sidebar.ISidebar;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

import java.util.UUID;

public interface IUser {

    /* User Data */
    String getName();
    UUID getUniqueId();

    /* Role */
    Role getRole();
    void setRole(@NotNull Role role);

    /* Sidebar */
    ISidebar getSidebar();
    void setSidebar(ISidebar sidebar);

    /* Checkpoint */
    ICheckpoint getCheckpoint();
    void setCheckpoint(ICheckpoint checkpoint);

    /* Bukkit Player */
    @Nullable Player asBukkit();

}
