package pl.mrstudios.deathrun.utils;

import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.objects.User;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ChannelUtil {

    public static void writeMessageAtChannel(User user, String channel, String action, Object message) {

        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

            dataOutputStream.writeUTF(action);
            dataOutputStream.writeUTF(String.valueOf(message));

            user.getPlayer().sendPluginMessage(Main.instance(), channel, byteArrayOutputStream.toByteArray());

        } catch (Exception ignored) {}

    }

}
