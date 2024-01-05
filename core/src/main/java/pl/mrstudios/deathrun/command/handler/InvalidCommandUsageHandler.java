package pl.mrstudios.deathrun.command.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.deathrun.config.Configuration;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {

    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;
    private final Configuration configuration;

    @Inject
    public InvalidCommandUsageHandler(MiniMessage miniMessage, BukkitAudiences audiences, Configuration configuration) {
        this.audiences = audiences;
        this.miniMessage = miniMessage;
        this.configuration = configuration;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        this.audiences.sender(invocation.sender()).sendMessage(this.miniMessage.deserialize(
                this.configuration.language().chatMessageInvalidCommandUsage
                        .replace("<usage>", result.getSchematic().first())
        ));
    }

}
