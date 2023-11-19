package pl.mrstudios.deathrun.api.arena.sidebar;

import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.user.IUser;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface ISidebar {

    /* Title */
    Component getTitle();
    ISidebar setTitle(Component component);

    /* Lines */
    Map<Team, Callable<Component>> getLines();
    ISidebar setLines(@NotNull List<ISidebarLine> lines);

    /* Functions */
    ISidebar create();
    void update();
    void destroy();

    ISidebar scheduleAutomaticUpdates(@NotNull Duration duration);
    ISidebar stopAutomaticUpdates();

    IUser getViewer();
    ISidebar setViewer(IUser user);

}
