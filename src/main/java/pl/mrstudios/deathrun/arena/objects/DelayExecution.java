package pl.mrstudios.deathrun.arena.objects;

import org.bukkit.scheduler.BukkitRunnable;
import pl.mrstudios.deathrun.Main;

public abstract class DelayExecution extends BukkitRunnable {

    public DelayExecution(int delay) {
        this.runTaskLater(Main.instance(), (20L * delay));
    }

    @Override
    public abstract void run();

}
