package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Sound;
import pl.mrstudios.deathrun.api.arena.booster.IBooster;
import pl.mrstudios.deathrun.api.arena.booster.IBoosterItem;
import pl.mrstudios.deathrun.api.arena.booster.enums.Direction;
import pl.mrstudios.deathrun.arena.booster.Booster;

public class BoosterSerializer implements ObjectSerializer<IBooster> {

    @Override
    public void serialize(@NonNull IBooster object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("slot", object.slot());
        data.add("power", object.power());
        data.add("delay", object.delay());
        data.add("item", object.item());
        data.add("delayItem", object.delayItem());
        data.add("direction", object.direction());
        data.add("sound", object.sound());
    }

    @Override
    public IBooster deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Booster(
                data.get("slot", Integer.class),
                data.get("power", Float.class),
                data.get("delay", Integer.class),
                data.get("item", IBoosterItem.class),
                data.get("delayItem", IBoosterItem.class),
                data.get("direction", Direction.class),
                data.get("sound", Sound.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super IBooster> type) {
        return IBooster.class.isAssignableFrom(type);
    }

}
