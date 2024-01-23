package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.pad.ITeleportPad;
import pl.mrstudios.deathrun.arena.pad.TeleportPad;

public class TeleportPadSerializer implements ObjectSerializer<ITeleportPad> {

    @Override
    public void serialize(@NotNull ITeleportPad object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("location", object.padLocation());
        data.add("teleport", object.teleportLocation());
    }

    @Override
    public ITeleportPad deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new TeleportPad(
                data.get("location", Location.class),
                data.get("teleport", Location.class)
        );
    }

    @Override
    public boolean supports(@NotNull Class<? super ITeleportPad> type) {
        return ITeleportPad.class.isAssignableFrom(type);
    }

}
