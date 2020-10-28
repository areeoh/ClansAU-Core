package com.areeoh.core.utility;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class UtilPlayer {

    public static void sendActionBar(Player player, String text) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(ppoc);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public static HashMap<LivingEntity, Double> getEntsInRadius(Location loc, double dR) {
        HashMap<LivingEntity, Double> ents = new HashMap<>();
        for (Entity cur : loc.getWorld().getEntities()) {
            if ((cur instanceof LivingEntity) && (isSpectator(cur))) {
                LivingEntity ent = (LivingEntity) cur;
                double offset = UtilMath.offset(loc, ent.getLocation());
                if (offset < dR) {
                    ents.put(ent, 1.0D - offset / dR);
                }
            }
        }
        return ents;
    }

    public static Player searchPlayer(Player player, String input, boolean inform) {
        if (Bukkit.getOnlinePlayers().stream().anyMatch(o -> (o).getName().equalsIgnoreCase(input))) {
            return Bukkit.getOnlinePlayers().stream().filter(o -> o.getName().equalsIgnoreCase(input)).findFirst().get();
        }
        LinkedList<Player> players = new LinkedList<>(Bukkit.getOnlinePlayers());
        players.removeIf(player1 -> !player1.getName().toLowerCase().contains(input.toLowerCase()));
        if (players.size() != 1) {
            if (inform) {
                UtilMessage.message(player, "Player Search", ChatColor.YELLOW.toString() + players.size() + ChatColor.GRAY + " Matches found [" + ChatColor.YELLOW + players.stream().map(HumanEntity::getName).collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.YELLOW)) + ChatColor.GRAY + "]");
            }
        }
        return players.size() == 1 ? players.get(0) : null;
    }

    public static boolean isSpectator(Entity player) {
        if ((player instanceof Player)) {
            return ((CraftPlayer) player).getHandle().isSpectator();
        }
        return false;
    }

    public static boolean isGrounded(Entity ent) {
        if ((ent instanceof CraftEntity)) {
            return ((CraftEntity) ent).getHandle().onGround;
        }
        return !UtilBlock.airFoliage(ent.getLocation().getBlock().getRelative(BlockFace.DOWN));
    }

    public static void addHealth(Player player, double add) {
        if (player.isDead()) {
            return;
        }
        double health = player.getHealth() + add;
        if (health < 0.0D) {
            health = 0.0D;
        }
        if (health > player.getMaxHealth()) {
            health = player.getMaxHealth();
        }
        player.setHealth(health);
    }

    public static int getPing(Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }

    public static ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
            for (int y = loc.getBlockY() - radius; y <= loc.getBlockY() + radius; y++) {
                for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
                    Location l = new Location(loc.getWorld(), x, y, z);
                    if (l.distance(loc) <= radius) {
                        blocks.add(l.getBlock());
                    }
                }
            }
        }
        return blocks;
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { UtilNMS.getNMSClass("Packet") })
                    .invoke(playerConnection, new Object[] { packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
