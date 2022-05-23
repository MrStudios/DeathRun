package pl.mrstudios.deathrun.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.config.list.MapConfig;
import pl.mrstudios.deathrun.config.list.SettingsConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Getter @Setter
public class Configuration {

    private static Gson gson = new Gson();

    /* Configuration Settings */
    private int configVersion = 3;

    /* Configurations */
    private MapConfig mapConfig;
    private SettingsConfig settings;

    public Configuration() {

        this.settings = this.load("settings.json", SettingsConfig.class);
        this.mapConfig = this.load("arena.json", MapConfig.class);

        /* Configuration Version Checking */
        if (configVersion != this.settings.getConfigVersion()) {

            File file = new File(Main.instance().getDataFolder(), "settings.json");

            if (file.exists())
                file.delete();

            this.settings = this.load("settings.json", SettingsConfig.class);

        }

    }

    public <T> T load(String fileName, Class<T> typeOfClass) {

        try {

            File file = new File(Main.instance().getDataFolder(), fileName);

            if (!file.exists())
                Main.instance().saveResource(file.getName(), false);

            FileReader fileReader = new FileReader(file);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            bufferedReader.lines().forEach(stringBuilder::append);

            fileReader.close();
            bufferedReader.close();

            return gson.fromJson(stringBuilder.toString(), typeOfClass);

        } catch (Exception exception) {
            Main.instance().getLogger().severe("Error while loading " + fileName + " file. (" + exception.getClass().getSimpleName() + ")");
        }

        return null;

    }

    public SettingsConfig.Settings getSettings() {
        return this.settings.getSettings();
    }
    public SettingsConfig.Messages getMessages() {
        return this.settings.getMessages();
    }

    @Getter @Setter
    public static class Adapter {}

}
