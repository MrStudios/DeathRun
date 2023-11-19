package pl.mrstudios.deathrun.api.arena.sidebar;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public interface ISidebarLine {

    /* Score Value */
    int getScore();
    void setScore(int value);

    /* Line Content */
    Callable<Component> getContent();
    void setContent(@NotNull Callable<Component> content);

}
