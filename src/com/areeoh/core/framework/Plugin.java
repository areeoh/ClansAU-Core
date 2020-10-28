package com.areeoh.core.framework;

import com.areeoh.core.utility.UtilMessage;
import com.areeoh.core.utility.UtilTime;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Plugin extends JavaPlugin {

    protected Set<Manager> managers = new HashSet<>();

    public <T extends Manager> T getManager(Class<T> classType) {
        for (Manager manager : getManagers()) {
            if (manager.getClass().equals(classType)) {
                return classType.cast(manager);
            }
        }
        return null;
    }

    public <T> Set<T> getManagers(Class<T> classType) {
        Set<T> managerSet = new HashSet<>();
        for (Manager manager : getManagers()) {
            if (classType.isAssignableFrom(manager.getClass())) {
                managerSet.add(classType.cast(manager));
            }
        }
        return managerSet;
    }

    public Manager getManager(String input) {
        for (Manager manager : getManagers()) {
            if (manager.getName().equalsIgnoreCase(input)) {
                return manager;
            }
        }
        return null;
    }

    public List<String> getManagersAsList() {
        List<String> managers = new ArrayList<>();
        for (Manager manager : getManagers()) {
            managers.add(manager.getName());
        }
        return managers;
    }

    protected void addModuleToManager(Module module, Manager manager) {
        manager.addModule(module);
        module.initialize(this);
    }

    protected void addManager(Manager manager) {
        if(manager.isEnabled()) {
            try {
                long epoch = System.currentTimeMillis();
                manager.initialize(this);
                System.out.println(manager.getName() + " initialised.");
                UtilMessage.log("Manager", manager.getName() + " initialised in " + UtilTime.convert(System.currentTimeMillis() - epoch, UtilTime.TimeUnit.SECONDS, 2) + ".");
            } catch (Exception ex) {
                ex.printStackTrace();
                UtilMessage.log("Error", "Failed to load " + manager.getName());
            }
        }
        getManagers().add(manager);
    }

    public abstract Set<Manager> getManagers();
}
