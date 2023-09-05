package pl.mrstudios.deathrun.api.version;

import net.kyori.adventure.text.Component;
import pl.mrstudios.deathrun.api.arena.interfaces.IUser;

public interface IVersionSupport {

    void sendActionBar(IUser user, Component text);
    void sendTitle(IUser user, Component title, Component subtitle, int fadeIn, int stay, int fadeOut);

    Component serialize(String string);

    String identifier();

}
