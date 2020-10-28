package com.areeoh.core.config;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;

public class ConfigManager extends Manager<Module> {

    public ConfigManager(Plugin plugin) {
        super(plugin, "ConfigManager");
    }

    @Override
    public void registerModules() {
    }
}
