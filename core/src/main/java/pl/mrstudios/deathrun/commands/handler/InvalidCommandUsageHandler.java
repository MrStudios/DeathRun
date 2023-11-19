package pl.mrstudios.deathrun.commands.handler;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import pl.mrstudios.commons.inject.annotation.Inject;

public class InvalidCommandUsageHandler implements InvalidUsageHandler<CommandSender> {

    private final MiniMessage miniMessage;
    private final BukkitAudiences audiences;

    @Inject
    public InvalidCommandUsageHandler(MiniMessage miniMessage, BukkitAudiences audiences) {
        this.audiences = audiences;
        this.miniMessage = miniMessage;
    }

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        this.audiences.sender(invocation.sender()).sendMessage(this.miniMessage.deserialize(
                String.format("<reset> <dark_red><b>*</b> <red>Invalid command usage, correct usage is <dark_red>%s<red>.", result.getSchematic().first())
        ));
    }

}
