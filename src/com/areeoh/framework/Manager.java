package com.areeoh.framework;

import com.areeoh.ClansAUCore;
import com.areeoh.framework.interfaces.IManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager<E extends Module> extends Frame implements IManager {

    private List<E> modules;

    public Manager(ClansAUCore plugin, String name) {
        super(plugin, name);

        modules = new ArrayList<>();
    }

    @Override
    public void initialize(JavaPlugin javaPlugin) {
        super.initialize(javaPlugin);

        registerModules();

        for (Module module : modules) {
            if (module.isEnabled()) {
                module.initialize(javaPlugin);
            }
        }
    }

    @Override
    public void shutdown() {

    }

    public List<E> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> classType) {
        return modules.stream().filter(module -> module.getClass().equals(classType)).findFirst().map(classType::cast).orElse(null);
    }

    public E getModule(String input) {
        for (E module : modules) {
            if (module.getName().equalsIgnoreCase(input)) {
                return module;
            }
        }
        return null;
    }

    public List<String> getModulesAsList() {
        List<String> modules = new ArrayList<>();
        for (Module module : this.modules) {
            modules.add(module.getName());
        }
        return modules;
    }

    public <T> List<T> getModules(Class<T> clazz) {
        List<T> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if (clazz.isAssignableFrom(module.getClass())) {
                modules.add(clazz.cast(module));
            }
        }
        return modules;
    }

    public void addModule(E module) {
        this.modules.add(module);
    }

    public abstract void registerModules();

}
