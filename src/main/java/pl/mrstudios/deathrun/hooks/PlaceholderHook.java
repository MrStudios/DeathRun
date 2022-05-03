package pl.mrstudios.deathrun.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mrstudios.deathrun.Main;
import pl.mrstudios.deathrun.arena.objects.User;

import java.util.Objects;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "deathrun";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SfenKer";
    }

    @Override
    public @NotNull String getVersion() {
        return Main.instance().getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {

        StringBuilder stringBuilder = new StringBuilder();

        if (Objects.isNull(player))
            return null;

        User user = Main.instance().getArena().getUser(Main.instance().getServer().getPlayer(player.getName()));

        switch (params.toLowerCase()) {

            case "arena_name": {
                stringBuilder.append(Main.instance().getArena().getMapConfig().getName());
                break;
            }

            case "role_name": {
                stringBuilder.append(user.getRole().getDisplayName());
                break;
            }

            case "role": {
                stringBuilder.append(user.getRole());
                break;
            }

            case "deaths": {
                stringBuilder.append(user.getDeaths());
                break;
            }

            case "checkpoint": {
                stringBuilder.append(user.getCheckpoint().getId());
                break;
            }

            case "finish_position": {
                stringBuilder.append(user.getFinishPosition());
                break;
            }

            case "version": {
                stringBuilder.append(Main.instance().getDescription().getVersion());
                break;
            }

            case "runner_amount": {
                stringBuilder.append(Main.instance().getArena().getRunners().size());
                break;
            }

            case "death_amount": {
                stringBuilder.append(Main.instance().getArena().getDeaths().size());
                break;
            }

            case "spectator_amount": {
                stringBuilder.append(Main.instance().getArena().getSpectators().size());
                break;
            }

            case "players_amount": {
                stringBuilder.append(Main.instance().getArena().getPlayers().size());
                break;
            }

            case "gamestate": {
                stringBuilder.append(Main.instance().getArena().getGameState());
                break;
            }

            default: {
                stringBuilder.append("Unknown Placeholder!");
                break;
            }

        }

        return stringBuilder.toString();

    }

}
