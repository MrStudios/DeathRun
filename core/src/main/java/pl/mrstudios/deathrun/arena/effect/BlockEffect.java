package pl.mrstudios.deathrun.arena.effect;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public record BlockEffect(
        Material blockType,
        PotionEffectType effectType,
        int amplifier,
        float duration
) {}
