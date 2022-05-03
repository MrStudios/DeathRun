package pl.mrstudios.deathrun.utils;

import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.objects.User;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

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

    public static String getWebsiteContent(String url) {

        try {

            StringBuilder stringBuilder = new StringBuilder();
            URLConnection urlConnection = new URL(url).openConnection();

            urlConnection.setRequestProperty("User-Agent", "MrStudios Browser");
            urlConnection.setDoOutput(false);

            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            bufferedReader.lines().collect(Collectors.toList()).forEach(stringBuilder::append);

            return stringBuilder.toString();

        } catch (Exception ignored) {}

        return null;

    }

}
