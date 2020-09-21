package com.areeoh.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UtilVelocity {

    public static Vector getTrajectory(Location from, Location to) {
        return getTrajectory(from.toVector(), to.toVector());
    }

    public static Vector getTrajectory2d(Vector from, Vector to) {
        return to.subtract(from).setY(0).normalize();
    }

    public static Vector getTrajectory(Vector from, Vector to) {
        return to.subtract(from).normalize();
    }

    public static Vector calculateVelocity(Vector from, Vector to, int heightGain) {
        // Gravity of a potion
        double gravity = 0.115;
        // Block locations
        int endGain = to.getBlockY() - from.getBlockY();
        double horizDist = Math.sqrt(UtilLocation.distanceSquared(from, to));
        // Height gain
        int gain = heightGain;
        double maxGain = Math.max(gain, (endGain + gain));
        // Solve quadratic equation for velocity
        double a = -horizDist * horizDist / (4 * maxGain);
        double b = horizDist;
        double c = -endGain;
        double slope = -b / (2 * a) - Math.sqrt(b * b - 4 * a * c) / (2 * a);
        // Vertical velocity
        double vy = Math.sqrt(maxGain * gravity);
        // Horizontal velocity
        double vh = vy / slope;
        // Calculate horizontal direction
        int dx = to.getBlockX() - from.getBlockX();
        int dz = to.getBlockZ() - from.getBlockZ();
        double mag = Math.sqrt(dx * dx + dz * dz);
        double dirx = dx / mag;
        double dirz = dz / mag;
        // Horizontal velocity components
        double vx = vh * dirx;
        double vz = vh * dirz;
        return new Vector(vx, vy, vz);
    }

    public static void velocity(Entity ent, double str, double yAdd, double yMax, boolean groundBoost) {
        velocity(ent, ent.getLocation().getDirection(), str, false, 0.0D, yAdd, yMax, groundBoost);
    }

    public static void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost) {
        if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
            return;
        }
        if (ySet) {
            vec.setY(yBase);
        }
        vec.normalize();
        vec.multiply(str);
        vec.setY(vec.getY() + yAdd);
        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }
        if ((groundBoost) && (UtilPlayer.isGrounded(ent))) {
            vec.setY(vec.getY() + 0.2D);
        }
        ent.setFallDistance(0.0F);
        ent.setVelocity(vec);
        if (ent instanceof Player) {
            //CAHManager.setIgnore((Player) ent, 2500L);
        }
    }
}