package pl.mrstudios.deathrun.arena.tasks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;
import pl.mrstudios.deathrun.arena.objects.Trap;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TrapCooldownTask extends BukkitRunnable {

    private List<Trap.Cooldown> cooldowns = new ArrayList<>();

    public TrapCooldownTask() { setInstance(this); }

    @Override
    public void run() {

        try {

            for (Trap.Cooldown cooldown : this.cooldowns)
                cooldown.removeTimeUnit();

        } catch (Exception ignored) {}

    }

    @Getter @Setter
    private static TrapCooldownTask instance;

}
