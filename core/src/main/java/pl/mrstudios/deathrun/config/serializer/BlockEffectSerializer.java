package pl.mrstudios.deathrun.config.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.deathrun.arena.effect.BlockEffect;

import static org.bukkit.potion.PotionEffectType.getByName;

public class BlockEffectSerializer implements ObjectSerializer<BlockEffect> {

    @Override
    public void serialize(
            @NotNull BlockEffect object,
            @NotNull SerializationData data,
            @NotNull GenericsDeclaration generics
    ) {
        data.add("block", object.blockType());
        data.add("effect", object.effectType().getName());
        data.add("amplifier", object.amplifier());
        data.add("duration", object.duration());
    }

    @Override
    public @NotNull BlockEffect deserialize(
            @NotNull DeserializationData data,
            @NotNull GenericsDeclaration generics
    ) {
        return new BlockEffect(
                data.get("block", Material.class),
                getByName(data.get("effect", String.class)),
                data.get("amplifier", Integer.class),
                data.get("duration", Float.class)
        );
    }

    @Override
    public boolean supports(@NotNull Class<? super BlockEffect> type) {
        return BlockEffect.class.isAssignableFrom(type);
    }

}
