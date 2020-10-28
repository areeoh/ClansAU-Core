package com.areeoh.core.database;

import com.areeoh.core.client.ClientRepository;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;

public class DatabaseManager extends Manager<Module> {

    public DatabaseManager(Plugin plugin) {
        super(plugin, "DatabaseManager");
    }

    @Override
    public void registerModules() {
        addModule(new ClientRepository(this));
    }
}