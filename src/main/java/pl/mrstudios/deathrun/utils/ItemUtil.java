package pl.mrstudios.deathrun.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

@Getter @Setter
public class ItemUtil {

    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemUtil(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemUtil(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemUtil(Material material, int amount, byte data) {
        this.itemStack = new ItemStack(material, amount, data);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemUtil name(String name) {
        this.itemMeta.setDisplayName(new MessageUtil(name).color().getMessage());
        return this;
    }

    public ItemUtil lore(String... lore) {
        this.itemMeta.setLore(new MessageUtil(lore).color().getMessages());
        return this;
    }

    public ItemUtil itemFlag(ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemUtil headTexture(String base64) {

        if (!this.getItemStack().getType().equals(Material.SKULL_ITEM))
            throw new RuntimeException("Material must be Material#SKULL_ITEM.");

        Field field;
        Method method;
        GameProfile gameProfile = new GameProfile(new UUID(base64.substring(base64.length() - 20).hashCode(), base64.substring(base64.length() - 10).hashCode()), "Player");

        gameProfile.getProperties().put("textures", new Property("textures", base64));

        try {

            method = ((SkullMeta) itemMeta).getClass().getDeclaredMethod("setProfile", GameProfile.class);
            method.setAccessible(true);

            method.invoke(itemMeta, gameProfile);

        } catch (Exception exception) {

            try {

                field = ((SkullMeta) itemMeta).getClass().getDeclaredField("profile");
                field.setAccessible(true);

                field.set(itemMeta, gameProfile);

                field.set(itemMeta, gameProfile);

            } catch (Exception ignored) {}

        }

        return this;

    }

    public ItemUtil enchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemUtil unbreakable() {
        this.itemMeta.spigot().setUnbreakable(!this.itemMeta.spigot().isUnbreakable());
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }

}
