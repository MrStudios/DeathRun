package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.serializer.SerializerException;
import pl.mrstudios.deathrun.api.arena.trap.ITrap;
import pl.mrstudios.deathrun.api.arena.trap.annotations.Serializable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrapSerializer implements ObjectSerializer<ITrap> {

    @Override
    public void serialize(@NotNull ITrap object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {

        List<Field> fields = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));

        data.add("button", object.getButton());
        data.addCollection("locations", object.getLocations(), Location.class);
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
    public ITrap deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {

        try {

            ITrap trap = (ITrap) Class.forName(data.get("class", String.class)).getDeclaredConstructor().newInstance();
            List<Field> fields = new ArrayList<>(Arrays.asList(trap.getClass().getDeclaredFields()));

            trap.setButton(data.get("button", Location.class));
            trap.setLocations(data.getAsList("locations", Location.class));
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
    public boolean supports(@NotNull Class<? super ITrap> type) {
        return ITrap.class.isAssignableFrom(type);
    }

}
