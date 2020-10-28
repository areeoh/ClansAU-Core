package com.areeoh.core.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilMessage {

    public static void message(CommandSender sender, String prefix, String message) {
        sender.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static void message(Player player, String prefix, String message) {
        player.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static void message(Player player, String[] strings) {
        for(String str : strings) {
            player.sendMessage(str);
        }
    }

    public static void broadcast(String prefix, String message) {
        Bukkit.broadcastMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static void log(String prefix, String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    public static String getFinalArg(String[] args, int start) {
        StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }
}
