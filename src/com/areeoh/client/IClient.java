package com.areeoh.client;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IClient {

    UUID getUUID();

    String getName();

    void setName(String name);

    String getPreviousName();

    void setPreviousName(String previousName);

    String getIPAddress();

    void setIPAddress(String IPAddress);

    Rank getRank();

    void setRank(Rank rank);

    boolean hasRank(Rank rank, boolean inform);

    boolean isAdministrating();

    void setAdministrating(boolean administrating);

    boolean isOnline();

    Player getPlayer();
}