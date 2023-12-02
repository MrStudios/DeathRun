package pl.mrstudios.deathrun.arena.user;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

import java.util.UUID;

@Getter @Setter
public class User implements IUser {

    /* User Profile */
    private String name;
    private UUID uniqueId;

    /* User Arena Data */
    private Role role;
    private ICheckpoint checkpoint;
    private int deaths;

    public User(@NotNull Player player) {

        /* Set User Profile*/
        this.name = player.getName();
        this.uniqueId = player.getUniqueId();

        /* Set User Arena Data */
        this.deaths = 0;
        this.role = Role.UNKNOWN;

    }

    @Override
    public @Nullable Player asBukkit() {
        return Bukkit.getServer().getPlayer(this.name);
    }

}
