package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Location;
import pl.mrstudios.deathrun.api.arena.checkpoint.ICheckpoint;
import pl.mrstudios.deathrun.arena.checkpoint.Checkpoint;

import java.util.List;

public class CheckpointSerializer implements ObjectSerializer<ICheckpoint> {

    @Override
    public void serialize(@NonNull ICheckpoint object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id", object.id());
        data.add("spawn", object.spawn());
        data.add("locations", object.locations());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ICheckpoint deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Checkpoint(
                data.get("id", Integer.class),
                data.get("spawn", Location.class),
                data.get("locations", List.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super ICheckpoint> type) {
        return ICheckpoint.class.isAssignableFrom(type);
    }

}
