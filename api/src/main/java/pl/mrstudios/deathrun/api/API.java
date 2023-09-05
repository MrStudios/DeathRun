package pl.mrstudios.deathrun.api;

import pl.mrstudios.deathrun.api.arena.interfaces.IArena;
import pl.mrstudios.deathrun.api.arena.interfaces.ITrap;
import pl.mrstudios.deathrun.api.version.IVersionSupport;

import java.util.HashMap;
import java.util.Map;

public class API {

    /* Plugin Data */
    public IArena ARENA;
    public IVersionSupport VERSION;

    /* Registry */
    public Map<String, Class<? extends ITrap>> TRAP_REGISTRY = new HashMap<>();

    /* Instance */
    public static API INSTANCE;

    /* Trap Registry */
    public void registerTrap(ITrap trap) {
        this.TRAP_REGISTRY.put(trap.getId(), trap.getClass());
    }

}
