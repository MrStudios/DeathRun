package pl.mrstudios.deathrun.commands.subcommands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class SubCommand {

    private String name;

    public abstract void onCommand(@NotNull Player player, @NotNull String[] args);
    public abstract List<String> onTabCompletion(@NotNull Player player, @NotNull String[] args);

}
