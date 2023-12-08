package pl.mrstudios.deathrun.arena.booster;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.api.arena.booster.IBoosterItem;

public record BoosterItem(
        @NotNull String name,
        @NotNull Material material,
        @Nullable String texture
) implements IBoosterItem {}
