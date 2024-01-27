package pl.mrstudios.deathrun.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.config.Configuration;

public class NoCommandPermissionsHandler implements MissingPermissionsHandler<CommandSender> {

    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public NoCommandPermissionsHandler(
            @NotNull MiniMessage miniMessage,
            @NotNull BukkitAudiences audiences,
            @NotNull Configuration configuration
    ) {
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @Override
    public void handle(
            @NotNull Invocation<CommandSender> invocation,
            @NotNull MissingPermissions missingPermissions,
            @NotNull ResultHandlerChain<CommandSender> resultHandlerChain
    ) {
        this.audiences.sender(invocation.sender()).sendMessage(this.miniMessage.deserialize(this.configuration.language().chatMessageNoPermissions));
    }

}
