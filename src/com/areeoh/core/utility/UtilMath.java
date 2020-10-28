package com.areeoh.core.utility;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class UtilMath {

    public static String formatInteger(int amount) {
        return String.format("%,.0f", Double.parseDouble(String.valueOf(amount)));
    }

    public static int roundToNextEven(double d) {
        int hlp = (int) Math.ceil(d);
        if (hlp % 2 == 0)
            return hlp;
        return hlp - 1;
    }

    public static Vector getTrajectory(Location from, Location to) {
        return getTrajectory(from.toVector(), to.toVector());
    }

    public static Vector getTrajectory2d(Vector from, Vector to) {
        return to.subtract(from).setY(0).normalize();
    }

    public static Vector getTrajectory(Vector from, Vector to) {
        return to.subtract(from).normalize();
    }

    public static double getAngle(Vector vec1, Vector vec2) {
        return vec1.angle(vec2) * 180.0F / Math.PI;
    }

    public static float getYawAngle(float yaw) {
        float angle = yaw;
        if (yaw < 0) {
            angle += 360;
        }
        return angle;
    }

    public static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static double randDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int randomInt(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public static int randomInt(int end) {
        return ThreadLocalRandom.current().nextInt(end);
    }

    public static double randomDouble(double start, double end) {
        return ThreadLocalRandom.current().nextDouble(start, end);
    }

    public static double randomDouble(double end) {
        return ThreadLocalRandom.current().nextDouble(end);
    }

    public static long randomLong(long end) {
        return ThreadLocalRandom.current().nextLong(end);
    }

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static String createRechargeBar(double cRecharge, double maxRecharge) {
        StringBuilder rechargeBar = new StringBuilder(ChatColor.GREEN + "");
        int progress = (int) (cRecharge / maxRecharge * 26.0D);
        for (int i = 0; i < 26 - progress; i++) {
            rechargeBar.append('▌');
        }
        rechargeBar.append(ChatColor.RED);
        for (int i = 0; i < progress; i++) {
            rechargeBar.append('▌');
        }
        return rechargeBar.toString();
    }

    public static String createReverseRechargeBar(double cRecharge, double maxRecharge) {
        StringBuilder rechargeBar = new StringBuilder(ChatColor.GREEN + "");
        int progress = (int) (cRecharge / maxRecharge * 26.0D);
        for (int i = 0; i < progress; i++) {
            rechargeBar.append('▌');
        }
        rechargeBar.append(ChatColor.RED);
        for (int i = 0; i < 26 - progress; i++) {
            rechargeBar.append('▌');
        }
        return rechargeBar.toString();
    }

    public static Set<Vector> getBlocksLine(Location a, Location b) {
        Set<Vector> vset = new HashSet<>();
        Vector pos1 = a.toVector();
        Vector pos2 = b.toVector();
        int x1 = pos1.getBlockX(), y1 = pos1.getBlockY(), z1 = pos1.getBlockZ();
        int x2 = pos2.getBlockX(), y2 = pos2.getBlockY(), z2 = pos2.getBlockZ(); //TAKEN FROM WORLDEDIT
        int tipx = x1, tipy = y1, tipz = z1;
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);
        if (dx + dy + dz == 0) {
            vset.add(new Vector(tipx, tipy, tipz));
        }
        final int max = Math.max(Math.max(dx, dy), dz);
        if (max == dx) {
            for (int domstep = 0; domstep <= dx; domstep++) {
                tipx = x1 + domstep * (x2 - x1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dx) * (y2 - y1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dx) * (z2 - z1 > 0 ? 1 : -1));

                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        if (max == dy) {
            for (int domstep = 0; domstep <= dy; domstep++) {
                tipy = y1 + domstep * (y2 - y1 > 0 ? 1 : -1);
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dy) * (x2 - x1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dy) * (z2 - z1 > 0 ? 1 : -1));

                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        if (max == dz) {
            for (int domstep = 0; domstep <= dz; domstep++) {
                tipz = z1 + domstep * (z2 - z1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dz) * (y2 - y1 > 0 ? 1 : -1));
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dz) * (x2 - x1 > 0 ? 1 : -1));

                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        return vset;
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR, double heightLimit) {
        HashMap<Block, Double> blockList = new HashMap<Block, Double>();
        int iR = (int) dR + 1;
        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {
                    if (Math.abs(y) <= heightLimit) {
                        Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y),
                                (int) (loc.getZ() + z));

                        double offset = offset(loc, curBlock.getLocation().add(0.5D, 0.5D, 0.5D));
                        if (offset <= dR) {
                            blockList.put(curBlock, Double.valueOf(1.0D - offset / dR));
                        }
                    }
                }
            }
        }
        return blockList;
    }
}