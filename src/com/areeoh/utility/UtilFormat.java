package com.areeoh.utility;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtilFormat {

    public static String cleanString(String input) {
        if (input.equals("TNT")) {
            return "TNT";
        }
        return WordUtils.capitalizeFully(input.toLowerCase().replaceAll("_", " "));
    }

    public static String getRandomID(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String searchList(Player player, List<String> objectList, String searchName, String input, boolean inform) {
        List<String> objects = new ArrayList<>();
        if (objectList.stream().anyMatch(string -> string.toLowerCase().equals(input.toLowerCase()))) {
            return objectList.stream().filter(object -> object.toLowerCase().equals(input.toLowerCase())).findFirst().get();
        }
        objectList.stream().filter(object -> object.toLowerCase().contains(input.toLowerCase())).forEach(objects::add);
        if (objects.size() == 1) {
            return objects.get(0);
        } else {
            if (inform) {
                UtilMessage.message(player, searchName + " Search", objects.size() + " Matches found [" + ChatColor.YELLOW + objects.stream().collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.YELLOW)) + ChatColor.GRAY + "]");
            }
        }
        return null;
    }

    public static String toString(List<?> list) {
        String string = list.toString();
        return string.replace("[", "").replaceAll("]", "");
    }

    public static String chunkToString(Chunk chunk) {
        return chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ();
    }
}