package pl.mrstudios.deathrun.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.IArena;
import pl.mrstudios.deathrun.api.arena.enums.GameState;
import pl.mrstudios.deathrun.api.arena.user.IUser;
import pl.mrstudios.deathrun.api.arena.user.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
public class Arena implements IArena {

    /* Arena Data */
    private String name;

    private long startTime;
    private int finishedRuns;

    private List<IUser> users;
    private GameState gameState;

    @Override
    public @Nullable IUser getUser(@NotNull String string) {
        return this.users.stream()
                .filter(
                        (user) -> user.getName().equals(string)
                ).findFirst()
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
                .filter(
                        (user) -> user.getName().equals(player.getName()) && user.getUniqueId().equals(player.getUniqueId())
                ).findFirst()
                .orElse(null);
    }

    @Override
    public Collection<IUser> getRunners() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.RUNNER))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<IUser> getDeaths() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.DEATH))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<IUser> getSpectators() {
        return this.users.stream()
                .filter((user) -> user.getRole().equals(Role.SPECTATOR))
                .collect(Collectors.toList());
    }

}
