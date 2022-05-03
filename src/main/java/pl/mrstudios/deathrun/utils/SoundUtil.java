package pl.mrstudios.deathrun.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSound(Player player, String sound) {

        try {

            player.playSound(player.getLocation(), Sound.valueOf(sound), 1, 1);

        } catch (Exception ignored) {}

    }

}
