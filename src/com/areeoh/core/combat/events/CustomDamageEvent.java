package com.areeoh.core.combat.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class CustomDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Entity damagee;
    private Entity damager;
    private Projectile projectile;
    private double damage;
    private EntityDamageEvent.DamageCause damageCause;
    private boolean ignoreArmor;
    private boolean knockback;
    private double knockbackAmount;
    private boolean ignoreRate;
    private long damageDelay;

    public CustomDamageEvent(Entity damagee, Entity damager, Projectile projectile, double damage, EntityDamageEvent.DamageCause damageCause, boolean ignoreArmor, boolean knockback, boolean ignoreRate) {
        this.damagee = damagee;
        this.damager = damager;
        this.projectile = projectile;
        this.damage = damage;
        this.damageCause = damageCause;
        this.ignoreArmor = ignoreArmor;
        this.knockback = knockback;
        this.ignoreRate = ignoreRate;
        this.damageDelay = 400L;
    }

    public Player getDamageePlayer() {
        if(damagee != null && damagee instanceof Player) {
            return (Player) damagee;
        }
        return null;
    }

    public Player getDamagerPlayer() {
        if(damager != null && damager instanceof Player) {
            return (Player) damager;
        }
        return null;
    }

    public LivingEntity getDamageeLivingEntity() {
        if(damagee != null && damagee instanceof LivingEntity) {
            return (LivingEntity) damagee;
        }
        return null;
    }

    public LivingEntity getDamagerLivingEntity() {
        if(damager != null && damager instanceof LivingEntity) {
            return (LivingEntity) damager;
        }
        return null;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    public Entity getDamagee() {
        return damagee;
    }

    public void setDamagee(Entity damagee) {
        this.damagee = damagee;
    }

    public Entity getDamager() {
        return damager;
    }

    public void setDamager(Entity damager) {
        this.damager = damager;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause) {
        this.damageCause = damageCause;
    }

    public boolean isIgnoreArmor() {
        return ignoreArmor;
    }

    public void setIgnoreArmor(boolean ignoreArmor) {
        this.ignoreArmor = ignoreArmor;
    }

    public boolean isKnockback() {
        return knockback;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public double getKnockbackAmount() {
        return knockbackAmount;
    }

    public void setKnockbackAmount(double knockbackAmount) {
        this.knockbackAmount = knockbackAmount;
    }

    public boolean isIgnoreRate() {
        return ignoreRate;
    }

    public void setIgnoreRate(boolean ignoreRate) {
        this.ignoreRate = ignoreRate;
    }

    public long getDamageDelay() {
        return damageDelay;
    }

    public void setDamageDelay(long damageDelay) {
        this.damageDelay = damageDelay;
    }
}
