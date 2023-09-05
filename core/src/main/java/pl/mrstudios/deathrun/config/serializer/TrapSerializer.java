package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.yaml.snakeyaml.serializer.SerializerException;
import pl.mrstudios.deathrun.api.arena.interfaces.ITrap;
import pl.mrstudios.deathrun.api.data.annotations.Serializable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrapSerializer implements ObjectSerializer<ITrap> {

    @Override
    public void serialize(@NonNull ITrap object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {

        List<Field> fields = new ArrayList<>();

        fields.addAll(Arrays.asList(object.getClass().getSuperclass().getDeclaredFields()));
        fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
        fields.forEach((field) -> {

            if (!field.isAnnotationPresent(Serializable.class))
                return;

            if (!field.canAccess(object))
                field.setAccessible(true);

            try {
                data.add(field.getName(), field.get(object));
            } catch (Exception ignored) {}

        });

        data.add("class", object.getClass().getName());

    }

    @Override
    public ITrap deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {

        try {

            List<Field> fields = new ArrayList<>();
            ITrap trap = (ITrap) Class.forName(data.get("class", String.class)).getDeclaredConstructor().newInstance();

            fields.addAll(Arrays.asList(trap.getClass().getSuperclass().getDeclaredFields()));
            fields.addAll(Arrays.asList(trap.getClass().getDeclaredFields()));
            fields.forEach((field) -> {

                if (!field.isAnnotationPresent(Serializable.class))
                    return;

                if (!field.canAccess(trap))
                    field.setAccessible(true);

                try {
                    field.set(trap, data.get(field.getName(), field.getType()));
                } catch (Exception ignored) {}

            });

            return trap;

        } catch (Exception ignored) {}

        throw new SerializerException("Unable to deserialize '" + data.get("class", String.class) + "', if this is bug please contact with support.");

    }

    @Override
    public boolean supports(@NonNull Class<? super ITrap> type) {
        return ITrap.class.isAssignableFrom(type);
    }

}
