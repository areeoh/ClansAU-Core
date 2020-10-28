package com.areeoh.core.menu;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.menu.listeners.MenuHandler;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MenuManager extends Manager<Module> {

    private final List<IMenu> menus;

    public MenuManager(Plugin plugin) {
        super(plugin, "MenuManager");
        this.menus = new ArrayList<>();
    }

    @Override
    public void registerModules() {
        addModule(new MenuHandler(this));
    }

    public List<IMenu> getMenus() {
        return menus;
    }

    public boolean isMenu(Inventory inventory) {
        return menus.stream().anyMatch(menu -> menu.getTitle().equalsIgnoreCase(inventory.getName()));
    }

    public Menu getMenu(Inventory inventory) {
        return (Menu) menus.stream().filter(menu -> menu.getTitle().equals(inventory.getName())).filter(menu -> inventory.getViewers().size() > 0).filter(menu -> menu.getPlayer().getUniqueId().equals(inventory.getViewers().get(0).getUniqueId())).findFirst().orElse(null);
    }
}