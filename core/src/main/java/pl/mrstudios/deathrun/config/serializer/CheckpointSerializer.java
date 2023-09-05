package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import pl.mrstudios.deathrun.api.arena.data.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.arena.data.checkpoint.Checkpoint;

import java.util.List;

public class CheckpointSerializer implements ObjectSerializer<ICheckpoint> {

    @Override
    public void serialize(@NonNull ICheckpoint object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id", object.id());
        data.add("locations", object.locations());
    }

    @Override
    public ICheckpoint deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Checkpoint(
                data.get("id", Integer.class),
                data.get("locations", List.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super ICheckpoint> type) {
        return ICheckpoint.class.isAssignableFrom(type);
    }

}
