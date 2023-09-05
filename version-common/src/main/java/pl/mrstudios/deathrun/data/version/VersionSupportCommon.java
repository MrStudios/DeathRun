package pl.mrstudios.deathrun.data.version;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import pl.mrstudios.deathrun.api.arena.interfaces.IUser;
import pl.mrstudios.deathrun.api.version.IVersionSupport;

import java.time.Duration;

public class VersionSupportCommon implements IVersionSupport {

    @Override
    public void sendActionBar(IUser user, Component text) {
        user.asBukkit().sendActionBar(text);
    }

    @Override
    public void sendTitle(IUser user, Component title, Component subtitle, int fadeIn, int stay, int fadeOut) {
        user.asBukkit().showTitle(
                Title.title(
                        title, subtitle, Title.Times.of(
                                Duration.ofMillis(fadeIn),
                                Duration.ofMillis(stay),
                                Duration.ofMillis(fadeOut)
                        )
                )
        );
    }

    @Override
    public Component serialize(String string) {
        return LEGACY.deserialize(string);
    }

    @Override
    public String identifier() {
        return null;
    }

    private final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

}
