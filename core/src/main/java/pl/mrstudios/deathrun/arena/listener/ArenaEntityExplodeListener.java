package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;

public class ArenaEntityExplodeListener implements Listener {

    @Inject
    public ArenaEntityExplodeListener() {}

    @EventHandler
    public void onEntityExplode(@NotNull EntityExplodeEvent event) {
        event.blockList().clear();
    }

}
