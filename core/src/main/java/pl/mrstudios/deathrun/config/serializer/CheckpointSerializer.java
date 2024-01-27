package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.arena.checkpoint.Checkpoint;

public class CheckpointSerializer implements ObjectSerializer<ICheckpoint> {

    @Override
    public void serialize(
            @NotNull ICheckpoint object,
            @NotNull SerializationData data,
            @NotNull GenericsDeclaration generics
    ) {
        data.add("id", object.id());
        data.add("spawn", object.spawn());
        data.addCollection("locations", object.locations(), Location.class);
    }

    @Override
    public @NotNull ICheckpoint deserialize(
            @NotNull DeserializationData data,
            @NotNull GenericsDeclaration generics
    ) {
        return new Checkpoint(
                data.get("id", Integer.class),
                data.get("spawn", Location.class),
                data.getAsList("locations", Location.class)
        );
    }

    @Override
    public boolean supports(@NotNull Class<? super ICheckpoint> type) {
        return ICheckpoint.class.isAssignableFrom(type);
    }

}
