package pl.mrstudios.deathrun.utils;

import pl.mrstudios.deathrun.Main;

public class JavaUtil {

    public static <T> T cast(Object object, Class<T> typeOfClass) {

        try {
            return typeOfClass.cast(object);
        } catch (Exception exception) {
            Main.instance().getLogger().severe("Can't cast " + object.getClass().getSimpleName() + " to " + typeOfClass.getSimpleName() + " class.");
        }

        return null;

    }

}
