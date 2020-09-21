package com.areeoh.utility;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private ItemStack item;

    private ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public static ItemBuilder builder(ItemStack item) {
        return new ItemBuilder(item);
    }

    public String getDisplayName() {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "";
    }

    public List<String> getLore() {
        return item.hasItemMeta() && item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<>();
    }

    public boolean isLeftClick() {
        return item.getItemMeta().getDisplayName().contains("Left-Click");
    }

    public boolean isRightClick() {
        return item.getItemMeta().getDisplayName().contains("Right-Click");
    }

    public ItemBuilder setMaterialData(MaterialData data) {
        item.setData(data);
        return this;
    }

    /*
    public ItemBuilder setPurchased(long price, int amount, boolean purchased, boolean requiresActivation, boolean active, Obtainable.Type objectType) {
        if (purchased) {
            if (objectType == Obtainable.Type.CONTAINER) {
                addLore("§rPrice: §a" + price + " §rGems");
                addLore("");
                addLore("§8Right-Click: §7Buy 1");
                addLore("§8Left-Click: §7Open");
            } else if (requiresActivation) {
                if (active) {
                    addLore("§8Left-Click: §7Deactivate");
                    item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
                } else {
                    addLore("§8Left-Click: §7Activate");
                    item.removeEnchantment(Enchantment.FIRE_ASPECT);

                }
            }
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
            setAmount(amount);
        } else {
            setAmount(0);
            if (price > 0) {
                addLore("§rPrice: §a" + price + " §rGems");
                addLore("");
                addLore("§8Right-Click: §7Buy 1");
            } else if (price == -1) {
                addLore("§7Only obtainable through Mystery Boxes");
            }
            item.removeEnchantment(Enchantment.FIRE_ASPECT);
        }
        return this;
    }
    */

    public static String getRawName(ItemStack item) {
        String name = "";
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            name = item.getItemMeta()
                    .getDisplayName();
            for (int i = 0; i < UtilString.countCharInString(name, '§'); i++) {
                name = name.replaceFirst(name.charAt(name.indexOf("§") + 1) + "", "");
                name = name.replace("§", "");
            }
        }
        return name;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setCustomName(String name) {
        if (name.isEmpty()) {
            return this;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta meta = item.getItemMeta();

        if (meta.hasLore()) {
            List<String> list = meta.getLore();
            //Splits the Game Description into different lines when it finds a /n
            String[] descriptionLines = lore.split("\n");
            for (String descLine : descriptionLines) {
                list.add("§f" + descLine);
            }

            meta.setLore(list);
        } else {
            List<String> array = new ArrayList<>();
            String[] descriptionLines = lore.split("\n");
            for (String descLine : descriptionLines) {
                array.add("§f" + descLine);
            }
            meta.setLore(array);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> list;
        if (meta.hasLore()) {
            list = meta.getLore();
            for (String string : lore) {
                list.add(string);
            }
        } else {
            list = new ArrayList<>();
            for (String string : lore) {
                list.add(string);
            }
        }
        meta.setLore(list);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLeftClick() {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayName() + " §7(Left-Click)");
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setRightClick() {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayName() + " §7(Right-Click)");
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return this;
    }

    public static ItemStack addItemFlags(ItemStack item, ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getItem() {
        return item;
    }


}
