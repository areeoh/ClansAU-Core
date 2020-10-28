package com.areeoh.core.scoreboard.listeners;

import com.areeoh.core.client.events.ClientJoinEvent;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.Updater;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.core.utility.UtilTime;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;

public class TabListListener extends Module<ScoreboardManager> implements Listener, Updater {

    public TabListListener(ScoreboardManager manager) {
        super(manager, "TabListListener");
    }

    @EventHandler
    public void onClientJoin(ClientJoinEvent event) {
        Bukkit.getOnlinePlayers().forEach(this::updateTabList);
    }

    @Update()
    public void onUpdate() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            updateTabList(online);
        }
    }

    public void updateTabList(Player player) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        ChatComponentText header = new ChatComponentText(ChatColor.GOLD + ChatColor.BOLD.toString() + "ClansAU Season 1" + "\n");
        ChatComponentText footer = new ChatComponentText("\n" + ChatColor.GOLD + ChatColor.BOLD.toString() + "Ping: " + ChatColor.YELLOW + connection.player.ping + ChatColor.GOLD + ChatColor.BOLD.toString() + " TPS: " + ChatColor.YELLOW + UtilTime.trim((MinecraftServer.getServer()).recentTps[0], 2.0D) + ChatColor.GOLD + ChatColor.BOLD.toString() + " Online: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().stream().filter(player::canSee).count());
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);

            a.set(packet, header);
            b.set(packet, footer);

            connection.sendPacket(packet);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            UtilMessage.log("Error", "TabListListener.class " + e.getMessage());
        }
    }
}
