package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.pad.ITeleportPad;
import pl.mrstudios.deathrun.arena.pad.TeleportPad;

public class TeleportPadSerializer implements ObjectSerializer<ITeleportPad> {

    @Override
    public void serialize(@NonNull ITeleportPad object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("location", object.padLocation());
        data.add("teleport", object.teleportLocation());
    }

    @Override
    public ITeleportPad deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new TeleportPad(
                data.get("location", Location.class),
                data.get("teleport", Location.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super ITeleportPad> type) {
        return ITeleportPad.class.isAssignableFrom(type);
    }

}
