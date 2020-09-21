package com.areeoh.client.events;

import com.areeoh.client.Client;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClientJoinEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Client client;
    private final boolean newClient;

    public ClientJoinEvent(Player player, Client client, boolean newClient) {
        super(player);
        this.client = client;
        this.newClient = newClient;
    }

    public Client getClient() {
        return client;
    }

    public boolean isNewClient() {
        return newClient;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}