package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import pl.mrstudios.deathrun.arena.effect.BlockEffect;

public class BlockEffectSerializer implements ObjectSerializer<BlockEffect> {

    @Override
    public void serialize(@NonNull BlockEffect object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("block", object.blockType());
        data.add("effect", object.effectType().getName());
        data.add("amplifier", object.amplifier());
        data.add("duration", object.duration());
    }

    @Override
    public BlockEffect deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new BlockEffect(
                data.get("block", Material.class),
                PotionEffectType.getByName(data.get("effect", String.class)),
                data.get("amplifier", Integer.class),
                data.get("duration", Float.class)
        );
    }

    @Override
    public boolean supports(@NonNull Class<? super BlockEffect> type) {
        return BlockEffect.class.isAssignableFrom(type);
    }

}
