package com.areeoh.core.framework;

import com.areeoh.core.framework.interfaces.IFrame;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.UpdateManager;
import com.areeoh.core.framework.updater.UpdateTask;
import com.areeoh.core.framework.updater.Updater;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

public abstract class Frame implements IFrame {

    private final Plugin plugin;
    private final String name;
    private boolean enabled = true;

    public Frame(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public <T extends Manager> T getManager(Class<T> managerType) {
        return plugin.getManager(managerType);
    }

    public Manager getManager(String input) {
        return plugin.getManager(input);
    }

    @Override
    public void initialize(Plugin javaPlugin) {
        if (this instanceof Listener) {
            getPlugin().getServer().getPluginManager().registerEvents((Listener) this, getPlugin());
        }
        if (this instanceof Updater) {
            for (Method method : this.getClass().getMethods()) {
                if (method.isAnnotationPresent(Update.class)) {
                    Update annotation = method.getAnnotation(Update.class);

                    getManager(UpdateManager.class).addTask(new UpdateTask(this, method, annotation.ticks(), annotation.async()));
                }
            }
        }
        setEnabled(true);
        onEnable();
    }

    @Override
    public void shutdown() {
        if (this instanceof Listener) {
            HandlerList.unregisterAll((Listener) this);
        }
        if (this instanceof CommandExecutor) {
            getPlugin().getCommand(this.getName()).setExecutor(null);
        }
        setEnabled(false);
        onDisable();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
