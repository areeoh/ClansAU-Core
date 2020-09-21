package com.areeoh.client;

import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Client implements IClient {

    private final UUID uuid;
    private String name;
    private String previousName;
    private Rank rank;
    private String ipAddress;
    private boolean administrating;

    public Client(UUID uuid) {
        this.uuid = uuid;
        this.rank = Rank.PLAYER;
        this.previousName = "";
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    @Override
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public boolean hasRank(Rank rank, boolean inform) {
        boolean hasRank = rank.ordinal() <= getRank().ordinal();
        if (!hasRank && inform) {
            if (getPlayer() != null) {
                UtilMessage.message(getPlayer(), "Permissions", "This requires Permission Rank [" + rank.getRankColor(false) + ChatColor.GRAY + "].");
            }
        }
        return hasRank;
    }

    @Override
    public boolean isAdministrating() {
        return administrating;
    }

    @Override
    public void setAdministrating(boolean administrating) {
        this.administrating = administrating;
    }

    @Override
    public boolean isOnline() {
        return Bukkit.getPlayer(getUUID()) != null;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(getUUID());
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPreviousName() {
        return previousName;
    }

    @Override
    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    @Override
    public String getIPAddress() {
        return ipAddress;
    }

    @Override
    public void setIPAddress(String IPAddress) {
        this.ipAddress = IPAddress;
    }
}