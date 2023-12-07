package pl.mrstudios.deathrun.arena.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.arena.listener.annotations.ArenaRegistrableListener;
import pl.mrstudios.deathrun.config.Configuration;
import pl.mrstudios.deathrun.util.ChannelUtil;

@ArenaRegistrableListener
public class ArenaClickItemListener implements Listener {

    private final Plugin plugin;
    private final Configuration configuration;

    @Inject
    public ArenaClickItemListener(Plugin plugin, Configuration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    @EventHandler
    public void onStepOnBlockEffect(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        if (event.getPlayer().getItemInHand() == null)
            return;

        if (event.getPlayer().getItemInHand().getType() != Material.RED_BED)
            return;

        ChannelUtil.connect(plugin, event.getPlayer(), this.configuration.plugin().server);

    }

}
