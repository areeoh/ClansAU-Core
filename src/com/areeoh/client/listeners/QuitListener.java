package com.areeoh.client.listeners;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.events.ClientQuitEvent;
import com.areeoh.framework.Module;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends Module<ClientManager> implements Listener {

    public QuitListener(ClientManager manager) {
        super(manager, "QuitListener");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        final Player player = event.getPlayer();
        Client client = getManager(ClientManager.class).getClient(player.getUniqueId());
        if(client == null) {
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent(new ClientQuitEvent(player, client));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClientQuit(ClientQuitEvent event) {
        if(event.getQuitMessage() != null) {
            Bukkit.broadcastMessage(event.getQuitMessage());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuitBroadcast(ClientQuitEvent event) {
        event.setQuitMessage(ChatColor.RED + "Quit> " + ChatColor.GRAY + event.getClient().getName());
    }
}