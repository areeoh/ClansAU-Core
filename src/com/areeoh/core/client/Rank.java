package com.areeoh.core.client;

import org.bukkit.ChatColor;

public enum Rank {

    PLAYER(ChatColor.YELLOW, "Player"),
    MEDIA(ChatColor.LIGHT_PURPLE, "Media"),
    HELPER(ChatColor.DARK_GREEN, "Helper"),
    MOD(ChatColor.AQUA, "Mod"),
    HEADMOD(ChatColor.AQUA, "Head Mod"),
    ADMIN(ChatColor.RED, "Admin"),
    OWNER(ChatColor.DARK_RED, "Owner"),
    DEVELOPER(ChatColor.DARK_RED, "Dev");

    private ChatColor chatColor;
    private String prefix;

    Rank(ChatColor chatColor, String prefix) {
        this.chatColor = chatColor;
        this.prefix = prefix;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRankColor(boolean bold) {
        return (getChatColor().toString() + (bold ? ChatColor.BOLD : "") + getPrefix());
    }
}