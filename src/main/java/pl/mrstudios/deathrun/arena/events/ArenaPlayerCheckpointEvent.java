package pl.mrstudios.deathrun.arena.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.enums.GameState;
import pl.mrstudios.deathrun.arena.enums.Role;
import pl.mrstudios.deathrun.arena.objects.Checkpoint;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.User;
import pl.mrstudios.deathrun.utils.MessageUtil;
import pl.mrstudios.deathrun.utils.SoundUtil;

public class ArenaPlayerCheckpointEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (event.getTo().getBlock().getType() != Material.PORTAL)
            return;

        User user = Main.instance().getArena().getUser(event.getPlayer());

        if (!user.getRole().equals(Role.RUNNER))
            return;

        if (!Main.instance().getArena().getGameState().equals(GameState.PLAYING))
            return;

        for (Checkpoint checkpoint : Main.instance().getConfiguration().getMapConfig().getCheckpoints()) {

            for (Location location : checkpoint.getPortalLocations())
                if (location.getX() == event.getPlayer().getLocation().getBlockX() && location.getY() == event.getPlayer().getLocation().getBlockY() && location.getZ() == event.getPlayer().getLocation().getBlockZ()) {

                    if (user.getCheckpoint().getId() >= checkpoint.getId())
                        break;

                    if (checkpoint.equals(Main.instance().getArena().getMapConfig().getCheckpoints().get(Main.instance().getArena().getMapConfig().getCheckpoints().size() - 1))) {
                        user.finish();
                        return;
                    }

                    event.getPlayer().sendTitle(
                            new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getCheckpointTitle().replace("<checkpointNumber>", String.valueOf(checkpoint.getId()))).color().getMessage(),
                            new MessageUtil(Main.instance().getConfiguration().getMessages().getTitle().getCheckpointSubTitle().replace("<checkpointNumber>", String.valueOf(checkpoint.getId()))).color().getMessage());
                    user.setCheckpoint(checkpoint);
                    SoundUtil.playSound(user.getPlayer(), Main.instance().getConfiguration().getSettings().getSounds().getArenaCheckpointReached());
                    break;

                }

        }

    }

}
