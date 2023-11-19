package pl.mrstudios.deathrun.arena.sidebar;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import pl.mrstudios.deathrun.api.arena.sidebar.ISidebarLine;

import java.util.concurrent.Callable;

@Getter @Setter
public class SidebarLine implements ISidebarLine {
    private int score;
    private Callable<Component> content;
}
