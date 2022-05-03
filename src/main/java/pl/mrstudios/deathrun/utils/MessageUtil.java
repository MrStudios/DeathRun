package pl.mrstudios.deathrun.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter @Setter
public class MessageUtil {

    private String message;
    private List<String> messages;

    public MessageUtil(String message) {
        this.message = message;
    }

    public MessageUtil(List<String> messages) {
        this.messages = messages;
    }

    public MessageUtil(String... messages) {

        this.messages = Arrays.stream(messages).collect(Collectors.toList());

    }

    public MessageUtil color() {

        if (Objects.isNull(message))
            for (int i = 0; i < this.messages.size(); i++)
                this.messages.set(i, ChatColor.translateAlternateColorCodes('&', this.messages.get(i)));
        else
            this.message = ChatColor.translateAlternateColorCodes('&', message);

        return this;

    }

    public void send(CommandSender sender) {

        this.color();

        if (Objects.isNull(message))
            this.messages.forEach(sender::sendMessage);
        else
            sender.sendMessage(message);

    }

}
