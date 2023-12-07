package pl.mrstudios.deathrun.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public static ItemStack createItemStack(Material material, String name) { // TODO: convert to adventure builder

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(miniMessageToLegacy(name));
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    protected static String miniMessageToLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("<red>", "&c")
                .replace("<green>", "&a")
                .replace("<yellow>", "&e")
                .replace("<blue>", "&9")
                .replace("<white>", "&f")
                .replace("<black>", "&0")
                .replace("<gray>", "&7")
                .replace("<dark_gray>", "&8")
                .replace("<gold>", "&6")
                .replace("<dark_red>", "&4")
                .replace("<dark_green>", "&2")
                .replace("<dark_blue>", "&1")
                .replace("<dark_aqua>", "&3")
                .replace("<dark_purple>", "&5")
                .replace("<aqua>", "&b")
                .replace("<light_purple>", "&d")
                .replace("<bold>", "&l")
                .replace("<italic>", "&o")
                .replace("<strikethrough>", "&m")
                .replace("<underline>", "&n")
                .replace("<reset>", "&r")
                .replace("<magic>", "&k")
                .replace("<b>", "&b"));
    }

}
