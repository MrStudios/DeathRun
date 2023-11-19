package pl.mrstudios.deathrun.api.arena.trap;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ITrapRegistry {

    @Nullable Class<? extends ITrap> get(String identifier);

    void register(String identifier, Class<? extends ITrap> trapClass);

    Set<String> list();

}
