package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;
import pl.mrstudios.deathrun.api.arena.booster.IBoosterItem;
import pl.mrstudios.deathrun.arena.booster.BoosterItem;

public class BoosterItemSerializer implements ObjectSerializer<IBoosterItem> {

    @Override
    public void serialize(@NonNull IBoosterItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.name());
        data.add("material", object.material());
    }

    @Override
    public IBoosterItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new BoosterItem(
                data.get("name", String.class),
                data.get("material", Material.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super IBoosterItem> type) {
        return IBoosterItem.class.isAssignableFrom(type);
    }

}
