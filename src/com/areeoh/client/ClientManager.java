package com.areeoh.client;

import com.areeoh.ClansAUCore;
import com.areeoh.client.listeners.JoinListener;
import com.areeoh.client.listeners.QuitListener;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import com.areeoh.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientManager extends Manager<Module> {

    private HashSet<Client> clientMap;

    public ClientManager(ClansAUCore plugin) {
        super(plugin, "ClientManager");
        this.clientMap = new HashSet<>();
    }

    @Override
    public void registerModules() {
        addModule(new JoinListener(this));
        addModule(new QuitListener(this));
    }

    public Client getClient(UUID uuid) {
        for(Client client : clientMap) {
            if(client.getUUID().equals(uuid)) {
                return client;
            }
        }
        return null;
    }

    public Client getClient(String name) {
        for(Client client : clientMap) {
            if(client.getName().equalsIgnoreCase(name)) {
                return client;
            }
        }
        return null;
    }

    public void addClient(Client client) {
        clientMap.add(client);
    }

    public Client searchClient(CommandSender player, String input, boolean sendMessage) {
        if (clientMap.stream().anyMatch(client -> client.getName().equalsIgnoreCase(input))) {
            return clientMap.stream().filter(client -> client.getName().equalsIgnoreCase(input)).findFirst().get();
        }
        ArrayList<Client> clients = new ArrayList<>(clientMap);
        clients.removeIf(client -> !client.getName().toLowerCase().contains(input.toLowerCase()));
        if (clients.size() == 1) {
            return clients.get(0);
        } else if(clients.size() > 1) {
            if (sendMessage) {
                UtilMessage.message(player, "Client Search", ChatColor.YELLOW.toString() + clients.size() + ChatColor.GRAY + " Matches found [" + clients.stream().map(c -> ChatColor.YELLOW + c.getName()).collect(Collectors.joining(ChatColor.GRAY + ", ")) + ChatColor.GRAY + "]");
            }
        } else {
            if (sendMessage) {
                UtilMessage.message(player, "Client Search", ChatColor.YELLOW.toString() + clients.size() + ChatColor.GRAY + " Matches found [" + ChatColor.YELLOW + input + ChatColor.GRAY + "]");
            }
        }
        return null;
    }
}