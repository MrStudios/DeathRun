package pl.mrstudios.deathrun.util;

import com.google.common.io.ByteArrayDataOutput;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static com.google.common.io.ByteStreams.newDataOutput;

public class ChannelUtil {

    @SuppressWarnings("UnstableApiUsage")
    public static void connect(
            @NotNull Plugin plugin,
            @NotNull Player player,
            @NotNull String server
    ) {

        ByteArrayDataOutput dataOutput = newDataOutput();

        dataOutput.writeUTF("Connect");
        dataOutput.writeUTF(server);

        player.sendPluginMessage(plugin, "BungeeCord", dataOutput.toByteArray());

    }

}
