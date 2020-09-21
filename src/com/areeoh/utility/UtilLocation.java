package com.areeoh.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class UtilLocation {

    public static double getHorizontalDistance(Location to, Location from) {
        double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
        double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));
        return Math.sqrt(x * x + z * z);
    }

    public static double distanceSquared(Vector from, Vector to) {
        double dx = to.getBlockX() - from.getBlockX();
        double dz = to.getBlockZ() - from.getBlockZ();

        return dx * dx + dz * dz;
    }

    public static Location mergeLocs(Location location, Location location1) {
        location.setX(location1.getX());
        location.setY(location1.getY());
        location.setZ(location1.getZ());
        return location;
    }

    public static double offset2d(Vector a, Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static String locationToString(Location location) {
        return "(" + Math.round(location.getX()) + ", " + Math.round(location.getY()) + ", " + Math.round(location.getZ()) + ")";
    }

    public static double offset2d(Location a, Location b) {
        return offset2d(a.toVector(), b.toVector());
    }

    public static boolean chunkOutline(Chunk chunk, Location check) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (z == 0 || z == 15 || x == 0 || x == 15) {
                    for (int i = -3; i < 3; i++) {
                        if ((chunk.getZ() * 16) + z == (check.getBlockZ() + i) && (chunk.getX() * 16) + x == (check.getBlockX() + i)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}