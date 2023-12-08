package pl.mrstudios.deathrun.builder;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder name(String name) {

        if (this.itemMeta == null)
            return this;

        this.itemMeta.setDisplayNameComponent(bungeeComponentSerializer.serialize(miniMessage.deserialize(name).decoration(TextDecoration.ITALIC, false)));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {

        if (this.itemMeta == null)
            return this;

        List<BaseComponent[]> components = new ArrayList<>();

        lore.stream()
                .map(miniMessage::deserialize)
                .map((component) -> component.decoration(TextDecoration.ITALIC, false))
                .map(bungeeComponentSerializer::serialize)
                .forEach(components::add);

        this.itemMeta.setLoreComponents(components);

        return this;

    }

    public ItemBuilder itemFlag(ItemFlag... itemFlags) {

        if (this.itemMeta == null)
            return this;

        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemStack build() {

        if (this.itemMeta != null)
            this.itemStack.setItemMeta(this.itemMeta);

        return this.itemStack;

    }

    /* Constants */
    private static final MiniMessage miniMessage = MiniMessage.builder()
            .build();
    private static final BungeeComponentSerializer bungeeComponentSerializer = BungeeComponentSerializer.get();

}
