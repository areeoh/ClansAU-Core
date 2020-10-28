package com.areeoh.core.client.listeners;

import com.areeoh.core.client.Client;
import com.areeoh.core.client.ClientManager;
import com.areeoh.core.client.Rank;
import com.areeoh.core.framework.Module;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends Module<ClientManager> implements Listener {

    public ChatListener(ClientManager manager) {
        super(manager, "ChatListener");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Client client = getManager().getClient(event.getPlayer().getUniqueId());
        if(client == null) {
            return;
        }
        Rank rank = client.getRank();
        String prefix = client.hasRank(Rank.PLAYER, false) ? "" : rank.getRankColor(true) + " ";
        Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.WHITE + ": " + event.getMessage());
    }
}