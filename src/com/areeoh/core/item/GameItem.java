package com.areeoh.core.item;

import com.areeoh.core.framework.Module;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GameItem extends Module<ItemManager> {

    private final ItemStack itemStack;

    public GameItem(ItemManager manager, String moduleName, ItemStack itemStack) {
        super(manager, moduleName);
        this.itemStack = itemStack;
    }

    public void onRightClickBlock(Player player) {
    }

    public void onRightClickAir(Player player) {
    }

    public void onRightClick(Player player) {
    }

    public void onLeftClickBlock(Player player) {
    }

    public void onLeftClickAir(Player player) {
    }

    public void onLeftClick(Player player) {
    }

    protected String getDisplayName() {
        if(!getItemStack().hasItemMeta()) {
            return null;
        }
        if(!getItemStack().getItemMeta().hasDisplayName()) {
            return null;
        }
        return getItemStack().getItemMeta().getDisplayName();
    }

    protected String[] getDescription() {
        if(!getItemStack().hasItemMeta()) {
            return null;
        }
        if(!getItemStack().getItemMeta().hasLore()) {
            return null;
        }
        return getItemStack().getItemMeta().getLore().toArray(new String[0]);
    }

    protected boolean isItem(ItemStack itemStack) {
        return this.itemStack.isSimilar(itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
