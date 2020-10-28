package com.areeoh.core.item;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;

public class ItemManager extends Manager<Module> {

    public ItemManager(Plugin plugin) {
        super(plugin, "ItemManager");
    }

    @Override
    public void registerModules() {
        addModule(new ItemHandler(this));
    }
}