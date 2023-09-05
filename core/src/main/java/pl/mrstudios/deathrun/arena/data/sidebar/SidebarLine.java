package pl.mrstudios.deathrun.arena.data.sidebar;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import pl.mrstudios.deathrun.api.arena.data.sidebar.ISidebarLine;

import java.util.concurrent.Callable;

@Getter @Setter
public class SidebarLine implements ISidebarLine {
    private int score;
    private Callable<Component> content;
}
