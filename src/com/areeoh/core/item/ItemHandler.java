package com.areeoh.core.item;

import com.areeoh.core.framework.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemHandler extends Module<ItemManager> implements Listener {

    public ItemHandler(ItemManager manager) {
        super(manager, "ItemHandler");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        for (GameItem gameItem : getManager().getModules(GameItem.class)) {
            if(event.getPlayer().getItemInHand() == null)
                continue;
            if(!gameItem.isItem(event.getPlayer().getItemInHand()))
                continue;

            switch (event.getAction()) {
                case LEFT_CLICK_AIR:
                    gameItem.onLeftClickAir(event.getPlayer());
                    gameItem.onLeftClick(event.getPlayer());
                    break;
                case LEFT_CLICK_BLOCK:
                    gameItem.onLeftClickBlock(event.getPlayer());
                    gameItem.onLeftClick(event.getPlayer());
                    break;
                case RIGHT_CLICK_AIR:
                    gameItem.onRightClickAir(event.getPlayer());
                    gameItem.onRightClick(event.getPlayer());
                    break;
                case RIGHT_CLICK_BLOCK:
                    gameItem.onRightClickBlock(event.getPlayer());
                    gameItem.onRightClick(event.getPlayer());
                    break;
            }
        }
    }
}
