package pl.mrstudios.deathrun.arena.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.utils.ItemUtil;
import pl.mrstudios.deathrun.utils.MessageUtil;

public class ArenaPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.getPlayer().teleport(Main.instance().getConfiguration().getMapConfig().getWaitingLobby().bukkit());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);

        event.getPlayer().getActivePotionEffects().forEach(potionEffect -> event.getPlayer().removePotionEffect(potionEffect.getType()));

        event.getPlayer().getInventory().clear();

        event.getPlayer().getInventory().setItem(8, new ItemUtil(Material.BED, 1).name(Main.instance().getConfiguration().getMessages().getItems().getLeave()).itemFlag(ItemFlag.HIDE_ATTRIBUTES).build());

        Main.instance().getArena().getPlayers().add(new User(event.getPlayer()));

        if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING) && !Main.instance().getArena().getGameState().equals(GameState.ENDING))
            event.setJoinMessage(new MessageUtil(Main.instance().getConfiguration().getMessages().getChat().getPlayerJoined()
                    .replace("<player>", event.getPlayer().getName())
                    .replace("<currentPlayers>", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("<maxPlayers>", String.valueOf(Main.instance().getConfiguration().getSettings().getArena().getLimits().getMaxPlayers()))
            ).color().getMessage());
        else
            event.setJoinMessage("");

        event.getPlayer().setAllowFlight(false);
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1, false));

        for (Player target : Main.instance().getServer().getOnlinePlayers()) {
            target.showPlayer(event.getPlayer());
            event.getPlayer().showPlayer(target);
        }

    }

}
