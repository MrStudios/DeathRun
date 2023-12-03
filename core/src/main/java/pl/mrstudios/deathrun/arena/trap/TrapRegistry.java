package pl.mrstudios.deathrun.arena.trap;

import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.api.arena.trap.ITrapRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TrapRegistry implements ITrapRegistry {

    private final Map<String, Class<? extends ITrap>> traps;

    public TrapRegistry() {
        this.traps = new HashMap<>();
    }

    @Override
    public @Nullable Class<? extends ITrap> get(String identifier) {
        return this.traps.getOrDefault(identifier.toUpperCase(), null);
    }

    @Override
    public void register(Class<? extends ITrap> trapClass) {
        this.register(trapClass.getSimpleName().replace("Trap", ""), trapClass);
    }

    @Override
    public void register(String identifier, Class<? extends ITrap> trapClass) {
        this.traps.put(identifier.toUpperCase(), trapClass);
    }

    @Override
    public Set<String> list() {
        return this.traps.keySet();
    }

}
