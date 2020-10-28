package com.areeoh.core.utility;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class UtilTitle {

    public static void sendTitle(Player player, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        try {
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);
                title = title.replaceAll("%player%", player.getDisplayName());
                Object e = UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatTitle = UtilNMS.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor subtitleConstructor = UtilNMS.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], UtilNMS.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
                UtilPlayer.sendPacket(player, titlePacket);
                e = UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = UtilNMS.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                subtitleConstructor = UtilNMS.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], UtilNMS.getNMSClass("IChatBaseComponent") });
                titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
                UtilPlayer.sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                Object e = UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = UtilNMS.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor subtitleConstructor = UtilNMS.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], UtilNMS.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
                UtilPlayer.sendPacket(player, subtitlePacket);
                e = UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = UtilNMS.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
                subtitleConstructor = UtilNMS.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { UtilNMS.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], UtilNMS.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
                UtilPlayer.sendPacket(player, subtitlePacket);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }
}
