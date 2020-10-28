package com.areeoh.core.bungee;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeHandler extends Manager<Module> {

    public BungeeHandler(Plugin plugin) {
        super(plugin, "BungeeHandler");
    }

    @Override
    public void registerModules() {
    }

    @Override
    public void onEnable() {
        int port = -1;
        String address = "localhost";
        try {
            if (!getPlugin().getDataFolder().exists()) {
                if (!getPlugin().getDataFolder().mkdir()) {
                    throw new RuntimeException("Could not create configuration folder!");
                }
            }
            File config = new File(getPlugin().getDataFolder(), "socket_config.yml");

            if (!config.exists()) {
                if (!config.createNewFile()) {
                    throw new RuntimeException("Could not create configuration file!");
                }
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(config);
                conf.set("port", 55555);
                conf.set("address", "localhost");
                conf.save(config);
                port = 55555;
            } else {
                port = YamlConfiguration.loadConfiguration(config).getInt("port");
                address = YamlConfiguration.loadConfiguration(config).getString("address");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        SocketManager.init(address, port);
    }

    public void onDisable() {
        SocketManager.sendCommand(SocketManager.Command.EXIT);
    }
}