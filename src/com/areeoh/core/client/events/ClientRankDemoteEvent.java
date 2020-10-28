package com.areeoh.core.client.events;

import com.areeoh.core.client.Client;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClientRankDemoteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final CommandSender sender;
    private final Client client;

    public ClientRankDemoteEvent(CommandSender sender, Client client) {
        this.sender = sender;
        this.client = client;
    }

    public CommandSender getSender() {
        return sender;
    }

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