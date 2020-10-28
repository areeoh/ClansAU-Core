package com.areeoh.core.client.listeners;

import com.areeoh.core.bungee.SocketAPI;
import com.areeoh.core.client.Client;
import com.areeoh.core.client.ClientManager;
import com.areeoh.core.client.ClientRepository;
import com.areeoh.core.client.events.ClientJoinEvent;
import com.areeoh.core.database.DatabaseManager;
import com.areeoh.core.framework.Module;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends Module<ClientManager> implements Listener {

    public JoinListener(ClientManager manager) {
        super(manager, "JoinListener");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        final Player player = event.getPlayer();
        Client client = getManager(ClientManager.class).getClient(player.getUniqueId());
        boolean newClient = false;
        if (client == null) {
            client = new Client(player.getUniqueId());
            client.setName(player.getName());
            newClient = true;
        } else {
            if (!client.getName().equals(player.getName())) {
                client.setPreviousName(client.getName());
                client.setName(player.getName());
            }
        }
        client.setIPAddress(player.getAddress().getAddress().getHostAddress());
        getManager(ClientManager.class).addClient(client);
        Bukkit.getServer().getPluginManager().callEvent(new ClientJoinEvent(player, client, newClient));
    }

    @EventHandler
    public void onClientJoin(ClientJoinEvent event) {
        if (event.isNewClient()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "New> " + ChatColor.GRAY + event.getClient().getName());
            getManager(DatabaseManager.class).getModule(ClientRepository.class).saveClient(event.getClient());
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + "Join> " + ChatColor.GRAY + event.getClient().getName());
        }
        SocketAPI.broadcastToServer(ChatColor.GREEN + "Areeoh joined");
    }
}