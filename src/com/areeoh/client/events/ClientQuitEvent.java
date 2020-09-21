package com.areeoh.client.events;

import com.areeoh.client.Client;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClientQuitEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Client client;
    private String quitMessage = null;

    public ClientQuitEvent(Player player, Client client) {
        super(player);
        this.client = client;
    }

    public String getQuitMessage() { return quitMessage; }

    public void setQuitMessage(String quitMessage) { this.quitMessage = quitMessage; }

    public Client getClient() {
        return client;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}