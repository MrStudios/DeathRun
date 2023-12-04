package pl.mrstudios.deathrun.config.serdes;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;
import pl.mrstudios.deathrun.config.serializer.BlockEffectSerializer;
import pl.mrstudios.deathrun.config.serializer.CheckpointSerializer;
import pl.mrstudios.deathrun.config.serializer.TeleportPadSerializer;
import pl.mrstudios.deathrun.config.serializer.TrapSerializer;

public class PluginSerdes implements OkaeriSerdesPack {

    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new TrapSerializer());
        registry.register(new CheckpointSerializer());
        registry.register(new TeleportPadSerializer());
        registry.register(new BlockEffectSerializer());
    }

}
