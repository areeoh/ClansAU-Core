package com.areeoh;

import com.areeoh.combat.CombatManager;
import com.areeoh.client.ClientManager;
import com.areeoh.client.commands.ClientCmdManager;
import com.areeoh.client.commands.ModuleCommand;
import com.areeoh.database.DatabaseManager;
import com.areeoh.framework.Manager;
import com.areeoh.framework.updater.UpdateManager;
import com.areeoh.recharge.RechargeManager;
import com.areeoh.scoreboard.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClansAUCore extends JavaPlugin {

    private Set<Manager> managers = new HashSet<>();

    @Override
    public void onEnable() {
        registerManagers();

        for (Manager manager : managers) {
            if (manager.isEnabled()) {
                manager.initialize(this);
                System.out.println(manager.getName() + " initialised.");
            }
        }
    }

    @Override
    public void onDisable() {
        for (Manager manager : managers) {
            manager.shutdown();
        }
    }

    private void registerManagers() {
        managers.add(new ClientManager(this));
        managers.add(new DatabaseManager(this));
        managers.add(new ScoreboardManager(this));

        managers.add(new UpdateManager(this));
        managers.add(new CombatManager(this));

        managers.add(new RechargeManager(this));

        managers.add(new ModuleCommand(this));
        managers.add(new ClientCmdManager(this));
    }

    public <T extends Manager> T getManager(Class<T> classType) {
        for (Manager manager : managers) {
            if (manager.getClass().equals(classType)) {
                return classType.cast(manager);
            }
        }
        return null;
    }

    public <T> Set<T> getManagers(Class<T> classType) {
        Set<T> managerSet = new HashSet<>();
        for (Manager manager : managers) {
            if (classType.isAssignableFrom(manager.getClass())) {
                managerSet.add(classType.cast(manager));
            }
        }
        return managerSet;
    }

    public Manager getManager(String input) {
        for (Manager manager : managers) {
            if (manager.getName().equalsIgnoreCase(input)) {
                return manager;
            }
        }
        return null;
    }

    public Set<Manager> getManagers() {
        return this.managers;
    }

    public List<String> getManagersAsList() {
        List<String> managers = new ArrayList<>();
        for (Manager manager : this.managers) {
            managers.add(manager.getName());
        }
        return managers;
    }
}
