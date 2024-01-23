package pl.mrstudios.deathrun.arena.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

import java.util.UUID;

public class User implements IUser {

    /* User Profile */
    private final String name;
    private final UUID uniqueId;

    /* User Arena Data */
    private Role role;
    private ICheckpoint checkpoint;
    private int deaths;

    public User(
            @NotNull Player player
    ) {

        /* Set User Profile*/
        this.name = player.getName();
        this.uniqueId = player.getUniqueId();

        /* Set User Arena Data */
        this.deaths = 0;
        this.role = Role.UNKNOWN;

    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override
    public @NotNull Role getRole() {
        return this.role;
    }

    @Override
    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    @Override
    public @NotNull ICheckpoint getCheckpoint() {
        return this.checkpoint;
    }

    @Override
    public void setCheckpoint(@NotNull ICheckpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    @Override
    public int getDeaths() {
        return this.deaths;
    }

    @Override
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public @Nullable Player asBukkit() {
        return Bukkit.getServer().getPlayer(this.name);
    }

}
