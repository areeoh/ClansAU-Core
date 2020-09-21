package com.areeoh.utility;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.*;

public class UtilBlock {

    private static HashSet<Byte> blockAirFoliageSet = new HashSet<>();
    private static HashSet<Byte> blockPassSet = new HashSet<>();

    public static boolean solid(byte block) {
        if (blockPassSet.isEmpty()) {
            blockPassSet.add((byte) 0);
            blockPassSet.add((byte) 6);
            blockPassSet.add((byte) 8);
            blockPassSet.add((byte) 9);
            blockPassSet.add((byte) 10);
            blockPassSet.add((byte) 11);
            blockPassSet.add((byte) 26);
            blockPassSet.add((byte) 27);
            blockPassSet.add((byte) 28);
            blockPassSet.add((byte) 30);
            blockPassSet.add((byte) 31);
            blockPassSet.add((byte) 32);
            blockPassSet.add((byte) 37);
            blockPassSet.add((byte) 38);
            blockPassSet.add((byte) 39);
            blockPassSet.add((byte) 40);
            blockPassSet.add((byte) 50);
            blockPassSet.add((byte) 51);
            blockPassSet.add((byte) 55);
            blockPassSet.add((byte) 59);
            blockPassSet.add((byte) 63);
            blockPassSet.add((byte) 64);
            blockPassSet.add((byte) 65);
            blockPassSet.add((byte) 66);
            blockPassSet.add((byte) 68);
            blockPassSet.add((byte) 69);
            blockPassSet.add((byte) 70);
            blockPassSet.add((byte) 71);
            blockPassSet.add((byte) 72);
            blockPassSet.add((byte) 75);
            blockPassSet.add((byte) 77);
            blockPassSet.add((byte) 76);
            blockPassSet.add((byte) 78);
            blockPassSet.add((byte) 83);
            blockPassSet.add((byte) 90);
            blockPassSet.add((byte) 92);
            blockPassSet.add((byte) 93);
            blockPassSet.add((byte) 94);
            blockPassSet.add((byte) 96);
            blockPassSet.add((byte) 101);
            blockPassSet.add((byte) 102);
            blockPassSet.add((byte) 104);
            blockPassSet.add((byte) 105);
            blockPassSet.add((byte) 106);
            blockPassSet.add((byte) 107);
            blockPassSet.add((byte) 111);
            blockPassSet.add((byte) 115);
            blockPassSet.add((byte) 116);
            blockPassSet.add((byte) 117);
            blockPassSet.add((byte) 118);
            blockPassSet.add((byte) 119);
            blockPassSet.add((byte) 120);
            blockPassSet.add((byte) 208);
        }
        return !blockPassSet.contains(block);
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR, double heightLimit) {
        HashMap<Block, Double> blockList = new HashMap<>();
        int iR = (int) dR + 1;
        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {
                    if (Math.abs(y) <= heightLimit) {
                        Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y),
                                (int) (loc.getZ() + z));

                        double offset = UtilMath.offset(loc, curBlock.getLocation().add(0.5D, 0.5D, 0.5D));
                        if (offset <= dR) {
                            blockList.put(curBlock, 1.0D - offset / dR);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    public static Set<Vector> getBlocksLine(Location a, Location b)
    {
        Set<Vector> vset = new HashSet<>();

        Vector pos1 = a.toVector();
        Vector pos2 = b.toVector();

        int x1 = pos1.getBlockX(), y1 = pos1.getBlockY(), z1 = pos1.getBlockZ();
        int x2 = pos2.getBlockX(), y2 = pos2.getBlockY(), z2 = pos2.getBlockZ(); //TAKEN FROM WORLDEDIT
        int tipx = x1, tipy = y1, tipz = z1;
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1), dz = Math.abs(z2 - z1);

        if (dx + dy + dz == 0)
        {
            vset.add(new Vector(tipx, tipy, tipz));
        }
        if (Math.max(Math.max(dx, dy), dz) == dx)
        {
            for (int domstep = 0; domstep <= dx; domstep++)
            {
                tipx = x1 + domstep * (x2 - x1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dx) * (y2 - y1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dx) * (z2 - z1 > 0 ? 1 : -1));

                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        if (Math.max(Math.max(dx, dy), dz) == dy)
        {
            for (int domstep = 0; domstep <= dy; domstep++)
            {
                tipy = y1 + domstep * (y2 - y1 > 0 ? 1 : -1);
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dy) * (x2 - x1 > 0 ? 1 : -1));
                tipz = (int) Math.round(z1 + domstep * ((double) dz) / ((double) dy) * (z2 - z1 > 0 ? 1 : -1));

                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        if (Math.max(Math.max(dx, dy), dz) == dz)
        {
            for (int domstep = 0; domstep <= dz; domstep++)
            {
                tipz = z1 + domstep * (z2 - z1 > 0 ? 1 : -1);
                tipy = (int) Math.round(y1 + domstep * ((double) dy) / ((double) dz) * (y2 - y1 > 0 ? 1 : -1));
                tipx = (int) Math.round(x1 + domstep * ((double) dx) / ((double) dz) * (x2 - x1 > 0 ? 1 : -1));
                vset.add(new Vector(tipx, tipy, tipz));
            }
        }
        return vset;
    }

    public static List<Location> sphere(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx + r; x++)
            for (int z = cz - r; z <= cz + r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
        return circleblocks;
    }

    public static boolean airFoliage(Block block) {
        if (block == null) {
            return false;
        }
        return airFoliage(block.getTypeId());
    }

    private static boolean airFoliage(int block) {
        return airFoliage((byte) block);
    }

    public static boolean airFoliage(byte block) {
        if (blockAirFoliageSet.isEmpty()) {
            blockAirFoliageSet.add((byte) 0);
            blockAirFoliageSet.add((byte) 6);
            blockAirFoliageSet.add((byte) 31);
            blockAirFoliageSet.add((byte) 32);
            blockAirFoliageSet.add((byte) 37);
            blockAirFoliageSet.add((byte) 38);
            blockAirFoliageSet.add((byte) 39);
            blockAirFoliageSet.add((byte) 40);
            blockAirFoliageSet.add((byte) 51);
            blockAirFoliageSet.add((byte) 59);
            blockAirFoliageSet.add((byte) 104);
            blockAirFoliageSet.add((byte) 105);
            blockAirFoliageSet.add((byte) 115);
            blockAirFoliageSet.add((byte) 102);
            blockAirFoliageSet.add((byte) 175);
            blockAirFoliageSet.add((byte) 166);
            blockAirFoliageSet.add((byte) 323);
            blockAirFoliageSet.add((byte) 68);
            blockAirFoliageSet.add((byte) 77);
            blockAirFoliageSet.add((byte) 143);
            blockAirFoliageSet.add((byte) 183);
            blockAirFoliageSet.add((byte) 184);
            blockAirFoliageSet.add((byte) 185);
            blockAirFoliageSet.add((byte) 186);
            blockAirFoliageSet.add((byte) 187);
        }
        return blockAirFoliageSet.contains(block);
    }
}