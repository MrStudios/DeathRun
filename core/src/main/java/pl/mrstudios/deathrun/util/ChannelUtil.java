package pl.mrstudios.deathrun.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ChannelUtil {

    public static void connect(Plugin plugin, Player player, String server) {

        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);

            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());

            dataOutputStream.close();
            byteArrayOutputStream.close();

        } catch (Exception ignored) {}

    }

}
