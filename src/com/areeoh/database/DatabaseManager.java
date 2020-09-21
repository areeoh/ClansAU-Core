package com.areeoh.database;

import com.areeoh.ClansAUCore;
import com.areeoh.client.ClientRepository;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;

public class DatabaseManager extends Manager<Module> {

    public DatabaseManager(ClansAUCore plugin) {
        super(plugin, "DatabaseManager");
    }

    @Override
    public void registerModules() {
        addModule(new ClientRepository(this));
    }
}