package pl.mrstudios.deathrun.api.arena.trap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ITrapRegistry {

    @Nullable Class<? extends ITrap> get(String identifier);

    void register(@NotNull Class<? extends ITrap> trapClass);

    void register(@NotNull String identifier, @NotNull Class<? extends ITrap> trapClass);

    @NotNull Set<String> list();

}
