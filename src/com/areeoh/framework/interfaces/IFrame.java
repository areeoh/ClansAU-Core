package com.areeoh.framework.interfaces;

import org.bukkit.plugin.java.JavaPlugin;

public interface IFrame {

    String getName();

    void initialize(JavaPlugin javaPlugin);

    void shutdown();
}
