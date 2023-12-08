package pl.mrstudios.deathrun.api.arena.booster;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IBoosterItem {
    @NotNull String name();
    @NotNull Material material();
    @Nullable String texture();
}
