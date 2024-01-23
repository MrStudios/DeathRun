package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.api.arena.booster.IBoosterItem;
import pl.mrstudios.deathrun.arena.booster.BoosterItem;

public class BoosterItemSerializer implements ObjectSerializer<IBoosterItem> {

    @Override
    public void serialize(@NotNull IBoosterItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {

        data.add("name", object.name());
        data.add("material", object.material());
        if (object.texture() != null)
            data.add("texture", object.texture());

    }

    @Override
    public IBoosterItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new BoosterItem(
                data.get("name", String.class),
                data.get("material", Material.class),
                (data.containsKey("texture")) ? data.get("texture", String.class) : null
        );
    }

    @Override
    public boolean supports(@NotNull Class<? super IBoosterItem> type) {
        return IBoosterItem.class.isAssignableFrom(type);
    }

}
