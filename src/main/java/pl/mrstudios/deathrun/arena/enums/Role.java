package pl.mrstudios.deathrun.arena.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mrstudios.deathrun.Main;

@Getter
@AllArgsConstructor
public enum Role {

    UNKNOWN("&cUnknown", Main.instance().getConfiguration().getMessages().getRoles().getLobby().getTabListPrefix()),
    RUNNER(Main.instance().getConfiguration().getMessages().getRoles().getRunner().getDisplayName(), Main.instance().getConfiguration().getMessages().getRoles().getRunner().getTabListPrefix()),
    DEATH(Main.instance().getConfiguration().getMessages().getRoles().getDeath().getDisplayName(), Main.instance().getConfiguration().getMessages().getRoles().getDeath().getTabListPrefix()),
    SPECTATOR(Main.instance().getConfiguration().getMessages().getRoles().getSpectator().getDisplayName(), Main.instance().getConfiguration().getMessages().getRoles().getSpectator().getTabListPrefix());

    private final String displayName;
    private final String tabListPrefix;

}
