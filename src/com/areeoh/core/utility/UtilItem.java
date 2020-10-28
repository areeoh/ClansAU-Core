package com.areeoh.core.utility;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UtilItem {


    public static ItemStack setItemNameAndLore(ItemStack item, String name, List<String> lore) {
        ItemMeta im = item.getItemMeta();

        im.setDisplayName(name);
        if (lore != null) {
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(Material material, int amount, String itemName, String[] itemDescription) {
        return createItem(material, amount, (byte) 0, itemName, itemDescription);
    }

    public static ItemStack createItem(Material material, String itemName, String[] itemDescription) {
        return createItem(material, 1, (byte) 0, itemName, itemDescription);
    }

    public static ItemStack createItem(Material material, int amount, byte itemID, String itemName, String[] itemDescription, boolean addGlow) {
        return addGlow ? addGlow(createItem(material, amount, itemID, itemName, itemDescription)) : createItem(material, itemID, itemName, itemDescription);
    }

    public static ItemStack createItem(Material material, byte itemID, String itemName, String[] itemDescription, boolean addGlow) {
        return addGlow ? addGlow(createItem(material, itemID, itemName, itemDescription)) : createItem(material, itemID, itemName, itemDescription);
    }

    private static ItemStack createItem(Material material, byte itemID, String itemName, String[] itemDescription) {
        return createItem(material, 1, itemID, itemName, itemDescription);
    }

    public static ItemStack createItem(Material material, int amount, byte itemID, String itemName, String[] itemDescription) {
        ItemStack itemStack = new ItemStack(material, amount, itemID);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(Arrays.asList(itemDescription));
        itemStack.setItemMeta(itemMeta);
        return removeAttributes(itemStack);
    }

    public static boolean isPower(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        if (!itemStack.getItemMeta().hasDisplayName()) {
            return false;
        }
        return ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).contains("Booster ");
    }

    public static boolean isBow(Material bow) {
        return ((isItem(bow)) && (bow == Material.BOW));
    }

    public static boolean isSword(Material swordType) {
        return ((isItem(swordType)) && (swordType == Material.IRON_SWORD)) || (swordType == Material.GOLD_SWORD) || (swordType == Material.DIAMOND_SWORD);
    }

    public static boolean isAxe(Material axeType) {
        return ((isItem(axeType)) && (axeType == Material.IRON_AXE)) || (axeType == Material.GOLD_AXE)
                || (axeType == Material.DIAMOND_AXE);
    }

    public static boolean isShovel(Material shovelType) {
        return (shovelType == Material.WOOD_SPADE
                || shovelType == Material.STONE_SPADE
                || shovelType == Material.IRON_SPADE
                || shovelType == Material.GOLD_SPADE
                || shovelType == Material.DIAMOND_SPADE);
    }

    public static boolean isPickAxe(Material pickType) {
        return (pickType == Material.WOOD_PICKAXE
                || pickType == Material.STONE_PICKAXE
                || pickType == Material.IRON_PICKAXE
                || pickType == Material.GOLD_PICKAXE
                || pickType == Material.DIAMOND_PICKAXE);
    }

    public static boolean isAxe1(Material axeType) {
        return (axeType == Material.IRON_AXE
                || axeType == Material.GOLD_AXE
                || axeType == Material.DIAMOND_AXE
                || axeType == Material.STONE_AXE
                || axeType == Material.WOOD_AXE);
    }

    public static boolean isWeapon(Material material) {
        return !(!isAxe(material) && !isSword(material));
    }

    public static ItemStack removeAttributes(ItemStack item) {
        if (!MinecraftReflection.isCraftItemStack(item)) {
            item = MinecraftReflection.getBukkitItemStack(item);
        }
        NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
        compound.put(NbtFactory.ofList("AttributeModifiers"));
        return item;
    }

    public static boolean isItem(Material itemType) {
        return itemType != null;
    }

    public static ItemStack updateNames(ItemStack abc) {
        if ((abc.hasItemMeta()) && (abc.getItemMeta().hasLore())) {
            if (!abc.getItemMeta().getLore().isEmpty()) {
                return abc;
            }
        }
        List<String> lore = new ArrayList<>();
        Material m = abc.getType();
        ItemMeta a = abc.getItemMeta();
        if (m == Material.LEATHER_HELMET) {
            a.setDisplayName("Assassin Helmet");
        } else if (m == Material.LEATHER_CHESTPLATE) {
            a.setDisplayName("Assassin Vest");
        } else if (m == Material.LEATHER_LEGGINGS) {
            a.setDisplayName("Assassin Leggings");
        } else if (m == Material.LEATHER_BOOTS) {
            a.setDisplayName("Assassin Boots");
        } else if (m == Material.IRON_HELMET) {
            a.setDisplayName("Knight Helmet");
        } else if (m == Material.IRON_CHESTPLATE) {
            a.setDisplayName("Knight Chestplate");
        } else if (m == Material.IRON_LEGGINGS) {
            a.setDisplayName("Knight Leggings");
        } else if (m == Material.IRON_BOOTS) {
            a.setDisplayName("Knight Boots");
        } else if (m == Material.DIAMOND_HELMET) {
            a.setDisplayName("Gladiator Helmet");
        } else if (m == Material.DIAMOND_CHESTPLATE) {
            a.setDisplayName("Gladiator Chestplate");
        } else if (m == Material.DIAMOND_LEGGINGS) {
            a.setDisplayName("Gladiator Leggings");
        } else if (m == Material.DIAMOND_BOOTS) {
            a.setDisplayName("Gladiator Boots");
        } else if (m == Material.GOLD_HELMET) {
            a.setDisplayName("Paladin Helmet");
        } else if (m == Material.GOLD_CHESTPLATE) {
            a.setDisplayName("Paladin Chestplate");
        } else if (m == Material.GOLD_LEGGINGS) {
            a.setDisplayName("Paladin Leggings");
        } else if (m == Material.GOLD_BOOTS) {
            a.setDisplayName("Paladin Boots");
        } else if (m == Material.CHAINMAIL_HELMET) {
            a.setDisplayName("Ranger Helmet");
        } else if (m == Material.CHAINMAIL_CHESTPLATE) {
            a.setDisplayName("Ranger Chestplate");
        } else if (m == Material.CHAINMAIL_LEGGINGS) {
            a.setDisplayName("Ranger Leggings");
        } else if (m == Material.CHAINMAIL_BOOTS) {
            a.setDisplayName("Ranger Boots");
        } else if (m == Material.GOLD_AXE) {
            a.setDisplayName("Radiant Axe");
            lore.add(ChatColor.WHITE + "Increases Axe Damage by 1.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "5");
        } else if (m == Material.GOLD_RECORD) {
            a.setDisplayName("$50,000");
        } else if (m == Material.GREEN_RECORD) {
            a.setDisplayName("$100,000");
        } else if (m == Material.RECORD_4) {
            a.setDisplayName("$500,000");
        } else if (m == Material.RECORD_11) {
            a.setDisplayName("$1,000,000");
        } else if (m == Material.CARROT_ITEM) {
            a.setDisplayName("Carrot");
        } else if (m == Material.POTATO_ITEM) {
            a.setDisplayName("Potato");
        } else if (m == Material.IRON_SWORD) {
            a.setDisplayName("Standard Sword");
            lore.add(ChatColor.WHITE + "Just a Standard Sword.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "5");
        } else if (m == Material.GOLD_SWORD) {
            a.setDisplayName("Radiant Sword");
            lore.add(ChatColor.WHITE + "Increases Sword Damage by 1.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "6");
        } else if (m == Material.DIAMOND_SWORD) {
            a.setDisplayName("Booster Sword");
            lore.add(ChatColor.WHITE + "Increases Sword Skill Level by 1.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "5");
        } else if (m == Material.IRON_AXE) {
            a.setDisplayName("Standard Axe");
            lore.add(ChatColor.WHITE + "Just a Standard Axe.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "4");
        } else if (m == Material.DIAMOND_AXE) {
            a.setDisplayName("Booster Axe");
            lore.add(ChatColor.WHITE + "Increases Axe Skill Level by 1.");
            lore.add("");
            lore.add(ChatColor.GRAY + "Damage: " + ChatColor.GREEN + "4");
        } else if (m == Material.LAPIS_BLOCK) {
            a.setDisplayName("Water Block");
        } else if (m == Material.APPLE) {
            a.setDisplayName("Energy Apple");
            lore.add(ChatColor.GRAY + "Right Click: " + ChatColor.YELLOW + "Consume");
            lore.add(ChatColor.GRAY + "Instantly restores 50 energy");
            lore.add("");
            lore.add(ChatColor.GRAY + "Cooldown: " + ChatColor.GREEN + "10" + ChatColor.GRAY + " seconds.");
        } else if (m == Material.BEACON) {
            a.setDisplayName(ChatColor.LIGHT_PURPLE + "Supply Crate");
            lore.add(ChatColor.WHITE + "Placing this block will call");
            lore.add(ChatColor.WHITE + "down a supply crate which contains");
            lore.add(ChatColor.WHITE + "a wide variety of items.");
            lore.add(" ");
            lore.add(ChatColor.WHITE + "This can only be placed in the wilderness.");
        } else if (m == Material.ENDER_PEARL) {
            a.setDisplayName(ChatColor.YELLOW + "Ethereal Pearl");
        } else if (m == Material.TNT) {
            a.setDisplayName(ChatColor.YELLOW + "TNT");
        } else if (m == Material.MOB_SPAWNER) {
            a.setDisplayName(ChatColor.YELLOW + "Agility Helmet");
        } else if (m == Material.SEA_LANTERN) {
            a.setDisplayName(ChatColor.LIGHT_PURPLE + "Farming Block");
            lore.add(ChatColor.GRAY + "Place this block down in your territory");
            lore.add(ChatColor.GRAY + "to allow crops to grow in that territory.");
        } else if (m == Material.INK_SACK) {
            if (abc.getDurability() == 1) {
                a.setDisplayName(ChatColor.YELLOW + "Rose Red");
            } else if (abc.getDurability() == 2) {
                a.setDisplayName(ChatColor.YELLOW + "Cactus Green");
            } else if (abc.getDurability() == 3) {
                a.setDisplayName(ChatColor.YELLOW + "Cocoa Beans");
            } else if (abc.getDurability() == 4) {
                a.setDisplayName(ChatColor.YELLOW + "Lapis Lazuli");
            } else if (abc.getDurability() == 5) {
                a.setDisplayName(ChatColor.YELLOW + "Purple Dye");
            } else if (abc.getDurability() == 6) {
                a.setDisplayName(ChatColor.YELLOW + "Cyan Dye");
            } else if (abc.getDurability() == 7) {
                a.setDisplayName(ChatColor.YELLOW + "Light Gray Dye");
            } else if (abc.getDurability() == 8) {
                a.setDisplayName(ChatColor.YELLOW + "Gray Dye");
            } else if (abc.getDurability() == 9) {
                a.setDisplayName(ChatColor.YELLOW + "Pink Dye");
            } else if (abc.getDurability() == 10) {
                a.setDisplayName(ChatColor.YELLOW + "Lime Dye");
            } else if (abc.getDurability() == 11) {
                a.setDisplayName(ChatColor.YELLOW + "Dandelion Yellow");
            } else if (abc.getDurability() == 12) {
                a.setDisplayName(ChatColor.YELLOW + "Light Blue Dye");
            } else if (abc.getDurability() == 13) {
                a.setDisplayName(ChatColor.YELLOW + "Magenta Dye");
            } else if (abc.getDurability() == 14) {
                a.setDisplayName(ChatColor.YELLOW + "Orange Dye");
            } else if (abc.getDurability() == 15) {
                a.setDisplayName(ChatColor.YELLOW + "Bone Meal");
            }
        } else if (m == Material.STAINED_CLAY) {
            if (abc.getDurability() == 15) {
                a.setDisplayName(ChatColor.YELLOW + "Gravity Bomb");
                lore.add(ChatColor.GRAY + "Left-Click: " + ChatColor.YELLOW + "Throw");
                lore.add(ChatColor.GRAY + "  Creates a field that disrupts all players caught inside");
            }
        } else if (m == Material.MAGMA_CREAM) {
            a.setDisplayName(ChatColor.YELLOW + "Incendiary Grenade");
            lore.add(ChatColor.GRAY + "Left-Click: " + ChatColor.YELLOW + "Throw");
            lore.add(ChatColor.GRAY + "  Burns people who enter the blast area");
        } else if (m == Material.WEB) {
            a.setDisplayName(ChatColor.YELLOW + "Throwing Web");
            lore.add(ChatColor.GRAY + "Left-Click: " + ChatColor.YELLOW + "Throw");
            lore.add(ChatColor.GRAY + "  Creates a Web Trap");
        } else if (m == Material.POTION) {
            a.setDisplayName(ChatColor.YELLOW + "Water Bottle");
            lore.add(ChatColor.GRAY + "Left-Click: " + ChatColor.YELLOW + "Throw");
            lore.add(ChatColor.GRAY + "  Douses Players");
            lore.add(ChatColor.GRAY + "  Douses Fires");
            lore.add("");
            lore.add(ChatColor.GRAY + "Right-Click: " + ChatColor.YELLOW + "Drink");
            lore.add(ChatColor.GRAY + "  Douses Self");
            lore.add(ChatColor.GRAY + "  Fire Resistance I for 4 Seconds");
        } else if (m == Material.ENCHANTMENT_TABLE) {
            a.setDisplayName(ChatColor.YELLOW + "Class Customisation");
        }
        if (a.hasDisplayName()) {
            if (a.getDisplayName().equalsIgnoreCase(ChatColor.stripColor("Base Fishing"))) {
                lore.add(ChatColor.WHITE + "Allows a player to fish inside their base");
            }
            if (!(a.getDisplayName().contains("Crate") || a.getDisplayName().contains("Farming Block"))) {
                a.setDisplayName(ChatColor.YELLOW + ChatColor.stripColor(a.getDisplayName()));
            }
        } else {
            a.setDisplayName(ChatColor.YELLOW + UtilFormat.cleanString(abc.getType().name()));
        }
        a.setLore(lore);
        abc.setItemMeta(a);
        if (getDurability(abc) != -1) {
            setLoreVar(abc, String.valueOf(getDurability(abc)));
        }
        return removeAttributes(abc);
    }

    public static void handleDurability(Player player, boolean affectArmor, boolean shootBow) {
        if (affectArmor) {
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack == null) {
                    continue;
                }
                int durability = getDurability(itemStack);
                if (durability == -1) {
                    continue;
                }
                if (getLoreVar(itemStack) != null) {
                    durability = Math.max(Integer.parseInt(getLoreVar(itemStack)) - 1, 0);
                }
                if (durability == 0) {
                    itemStack.setType(Material.AIR);
                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                    return;
                }
                setLoreVar(itemStack, String.valueOf(durability));
                float val = (float) durability / getDurability(itemStack);
                itemStack.setDurability((short) Math.max(1, (itemStack.getType().getMaxDurability() - (val * itemStack.getType().getMaxDurability()))));
            }
            return;
        }
        ItemStack itemStack = player.getItemInHand();
        int durability = getDurability(itemStack);
        if (shootBow) {
            if (isBow(itemStack.getType())) {
                durability = 385;
                if (getLoreVar(itemStack) != null) {
                    durability = Math.max(Integer.parseInt(getLoreVar(itemStack)) - 1, 0);
                }
                if (durability <= 0) {
                    player.setItemInHand(null);
                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                    return;
                }
                float val = (float) durability / getDurability(itemStack);
                itemStack.setDurability((short) Math.max(1, (itemStack.getType().getMaxDurability() - (val * itemStack.getType().getMaxDurability()))));
                setLoreVar(itemStack, String.valueOf(durability));
            }
            return;
        }
        if (itemStack.getType().name().equals("FISHING_ROD") || itemStack.getType().name().contains("_PICKAXE") || itemStack.getType().name().contains("_AXE") || itemStack.getType().name().contains("_SPADE") || itemStack.getType().name().contains("_SWORD") || itemStack.getType().name().contains("_HOE")) {
            if (getLoreVar(itemStack) != null) {
                durability = Math.max(Integer.parseInt(getLoreVar(itemStack)) - 1, 0);
            }
            if (durability <= 0) {
                player.setItemInHand(null);
                player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                return;
            }
            ItemStack is = itemStack.clone();
            setLoreVar(is, String.valueOf(durability));
            float val = (float) durability / getDurability(is);
            is.setDurability((short) Math.max(1, (is.getType().getMaxDurability() - (val * is.getType().getMaxDurability()))));
            player.getInventory().setItemInHand(is);
        }
    }

    private static String getLoreVar(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return null;
        }
        if (meta.getLore() == null) {
            return null;
        }
        for (String cur : meta.getLore()) {
            if (cur.contains("Durability")) {
                int index = "Durability".split(" ").length;
                String[] tokens = cur.split(" ");
                StringBuilder out = new StringBuilder();
                for (int i = index; i < tokens.length; i++) {
                    out.append(tokens[i]).append(" ");
                }
                if (out.length() > 0) {
                    out = new StringBuilder(out.substring(0, out.length() - 1));
                }
                return out.toString();
            }
        }
        return null;
    }

    public static void setLoreVar(ItemStack stack, String value) {
        if (stack == null) {
            return;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return;
        }
        ArrayList<String> newLore = new ArrayList<>();
        boolean inserted = false;
        if (meta.getLore() != null) {
            for (String lore : meta.getLore()) {
                if (!lore.contains("Durability")) {
                    newLore.add(lore);
                } else {
                    newLore.add(ChatColor.GRAY + "Durability" + ":" + ChatColor.GREEN + " " + value);
                    inserted = true;
                }
            }
        }
        if (!inserted) {
            newLore.add(ChatColor.GRAY + "Durability:" + ChatColor.GREEN + " " + value);
        }
        meta.setLore(newLore);
        stack.setItemMeta(meta);
    }

    public static ItemStack addGlow(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = null;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        }
        if (tag == null)
            tag = nmsStack.getTag();
        NBTTagList ench = new NBTTagList();
        tag.set("ench", ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public static boolean remove(Player player, Material item, byte data, int toRemove) {
        if (!contains(player, item, data, toRemove)) {
            return false;
        }
        for (int i : player.getInventory().all(item).keySet()) {
            if (toRemove <= 0) {
                continue;
            }
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getData() == null || stack.getData().getData() == data) {
                int foundAmount = stack.getAmount();
                if (toRemove >= foundAmount) {
                    toRemove -= foundAmount;
                    player.getInventory().setItem(i, null);
                    continue;
                }
                stack.setAmount(foundAmount - toRemove);
                player.getInventory().setItem(i, stack);
                toRemove = 0;
            }
        }
        player.updateInventory();
        return true;
    }

    public static boolean contains(Player player, Material item, byte data, int required) {
        for (int i : player.getInventory().all(item).keySet()) {
            if (required <= 0) {
                return true;
            }
            ItemStack stack = player.getInventory().getItem(i);
            if ((stack != null) && (stack.getAmount() > 0) && ((stack.getData() == null) || (stack.getData().getData() == data))) {
                required -= stack.getAmount();
            }
        }
        return required <= 0;
    }

    public static void insert(Player player, ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) {
            return;
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(stack);
        } else {
            player.getWorld().dropItem(player.getLocation(), stack);
        }
        player.updateInventory();
    }

    public static int getDurability(ItemStack itemStack) {
        Material material = itemStack.getType();
        if (material == Material.DIAMOND_SWORD) {
            return 1500;
        } else if (material == Material.GOLD_SWORD) {
            return 750;
        } else if (material == Material.IRON_SWORD) {
            return 500;
        } else if (material == Material.STONE_SWORD) {
            return 150;
        } else if (material == Material.WOOD_SWORD) {
            return 50;
        } else if (material == Material.DIAMOND_AXE) {
            return 1000;
        } else if (material == Material.GOLD_AXE) {
            return 500;
        } else if (material == Material.IRON_AXE) {
            return 300;
        } else if (material == Material.STONE_AXE) {
            return 150;
        } else if (material == Material.WOOD_AXE) {
            return 50;
        } else if (material == Material.DIAMOND_PICKAXE) {
            return 1750;
        } else if (material == Material.GOLD_PICKAXE) {
            return 500;
        } else if (material == Material.IRON_PICKAXE) {
            return 500;
        } else if (material == Material.STONE_PICKAXE) {
            return 150;
        } else if (material == Material.WOOD_PICKAXE) {
            return 50;
        } else if (material == Material.DIAMOND_SPADE) {
            return 1500;
        } else if (material == Material.GOLD_SPADE) {
            return 500;
        } else if (material == Material.IRON_SPADE) {
            return 500;
        } else if (material == Material.STONE_SPADE) {
            return 150;
        } else if (material == Material.WOOD_SPADE) {
            return 50;
        } else if (material == Material.DIAMOND_HOE) {
            return 1500;
        } else if (material == Material.GOLD_HOE) {
            return 500;
        } else if (material == Material.IRON_HOE) {
            return 500;
        } else if (material == Material.STONE_HOE) {
            return 150;
        } else if (material == Material.WOOD_HOE) {
            return 50;
        } else if (material.name().contains("_HELMET")) {
            return 300;
        } else if (material.name().contains("_CHESTPLATE")) {
            return 500;
        } else if (material.name().contains("_LEGGINGS")) {
            return 400;
        } else if (material.name().contains("_BOOTS")) {
            return 200;
        } else if (material == Material.FISHING_ROD) {
            return 128;
        } else if (material == Material.BOW) {
            return 385;
        }
        return -1;
    }

    public static void appendMeta(Plugin plugin, Entity entity, String meta, String object) {
        if (entity.hasMetadata(meta)) {
            entity.setMetadata(meta, new FixedMetadataValue(plugin, entity.getMetadata(meta).get(0)
                    .asString() + "," + object));
        } else {
            entity.setMetadata(meta, new FixedMetadataValue(plugin, object));
        }
    }

    public static boolean hasMetadata(Entity entity, String meta, String object) {
        if (!entity.hasMetadata(meta)) {
            return false;
        }

        return !entity.getMetadata(meta).isEmpty() && entity.getMetadata(meta).get(0).asString().contains(object);
    }

    public static UUID getEntityOwner(Entity entity, String meta) {
        if (!entity.hasMetadata(meta)) {
            return null;
        }

        if (entity.getMetadata(meta).isEmpty()) {
            return null;
        }
        String metaString = entity.getMetadata(meta).get(0).asString();
        String[] stringArray = metaString.split(",");

        for (String string : stringArray) {
            if (string.startsWith("UUID:")) {
                return UUID.fromString(string.split(":")[1]);
            }
        }

        return null;
    }

    public static ItemStack setMetadata(ItemStack item, String metadata, Object value) {
        return CraftItemStack.asBukkitCopy(setMetadata(CraftItemStack.asNMSCopy(item), metadata, value));
    }

    public static net.minecraft.server.v1_8_R3.ItemStack setMetadata(net.minecraft.server.v1_8_R3.ItemStack item, String metadata, Object value) {
        if (item.getTag() == null) {
            item.setTag(new NBTTagCompound());
        }
        setTag(item.getTag(), metadata, value);
        return item;
    }

    public static boolean hasMetadata(ItemStack item, String metadata) {
        return hasMetadata(CraftItemStack.asNMSCopy(item), metadata);
    }

    public static boolean hasMetadata(net.minecraft.server.v1_8_R3.ItemStack item, String metadata) {
        return item.getTag() != null && item.getTag().hasKey(metadata);
    }

    public static Object getMetadata(ItemStack item, String metadata) {
        return getMetadata(CraftItemStack.asNMSCopy(item), metadata);
    }

    public static Object getMetadata(net.minecraft.server.v1_8_R3.ItemStack item, String metadata) {
        if (!hasMetadata(item, metadata)) return null;
        return getObject(item.getTag().get(metadata));
    }

    public static NBTTagCompound setTag(NBTTagCompound tag, String tagString, Object value) {
        NBTBase base = null;
        if (value instanceof Boolean) {
            base = new NBTTagByte((byte) ((Boolean) value ? 1 : 0));
        } else if (value instanceof Long) {
            base = new NBTTagLong((Long) value);
        } else if (value instanceof Integer) {
            base = new NBTTagInt((Integer) value);
        } else if (value instanceof Byte) {
            base = new NBTTagByte((Byte) value);
        } else if (value instanceof Double) {
            base = new NBTTagDouble((Double) value);
        } else if (value instanceof Float) {
            base = new NBTTagFloat((Float) value);
        } else if (value instanceof String) {
            base = new NBTTagString((String) value);
        } else if (value instanceof Short) {
            base = new NBTTagShort((Short) value);
        }
        if (base != null) {
            tag.set(tagString, base);
        }
        return tag;
    }

    @SuppressWarnings("unchecked")
    public static Object getObject(NBTBase tag) {
        if (tag instanceof NBTTagEnd) {
            return null;
        } else if (tag instanceof NBTTagLong) {
            return ((NBTTagLong) tag).c();
        } else if (tag instanceof NBTTagByte) {
            return ((NBTTagByte) tag).f();
        } else if (tag instanceof NBTTagShort) {
            return ((NBTTagShort) tag).e();
        } else if (tag instanceof NBTTagInt) {
            return ((NBTTagInt) tag).d();
        } else if (tag instanceof NBTTagFloat) {
            return ((NBTTagFloat) tag).h();
        } else if (tag instanceof NBTTagDouble) {
            return ((NBTTagDouble) tag).g();
        } else if (tag instanceof NBTTagByteArray) {
            return ((NBTTagByteArray) tag).c();
        } else if (tag instanceof NBTTagString) {
            return ((NBTTagString) tag).a_();
        } else if (tag instanceof NBTTagList) {
            List<NBTBase> list = null;
            try {
                Field field = tag.getClass().getDeclaredField("list");
                field.setAccessible(true);
                list = (List<NBTBase>) field.get(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (list == null) return null;
            List<Object> toReturn = Lists.newArrayList();
            for (NBTBase base : list) {
                toReturn.add(getObject(base));
            }
            return toReturn;
        } else if (tag instanceof NBTTagCompound) {
            return tag;
        } else if (tag instanceof NBTTagIntArray) {
            return ((NBTTagIntArray) tag).c();
        }
        return null;
    }

    public static void setNBTTag(ItemStack itemStack, String key, NBTBase value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound comp = nmsItem.getTag();

        comp.set(key, value);

        nmsItem.setTag(comp);

        ItemMeta itemMeta = CraftItemStack.getItemMeta(nmsItem);
        itemStack.setItemMeta(itemMeta);
    }

    public static void removeNBTTag(ItemStack itemStack, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound comp = nmsItem.getTag();

        comp.remove(key);

        nmsItem.setTag(comp);

        ItemMeta itemMeta = CraftItemStack.getItemMeta(nmsItem);
        itemStack.setItemMeta(itemMeta);
    }

    public static NBTBase getNBTTag(ItemStack itemStack, String key) {
        try {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound comp = nmsItem.getTag();
            return comp.get(key);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static int getNBTTagInt(ItemStack itemStack, String key, int defaultValue) {
        try {
            net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound comp = nmsItem.getTag();
            return comp.getInt(key);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}