package com.areeoh.core.combat.events;

import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomDeathEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final EntityLiving entityDamagee;
    private final EntityLiving entityDamager;

    public CustomDeathEvent(EntityLiving entityDamagee, EntityLiving entityDamager) {
        this.entityDamagee = entityDamagee;
        this.entityDamager = entityDamager;
    }

    public EntityLiving getEntityDamagee() {
        return entityDamagee;
    }

    public EntityLiving getEntityDamager() {
        return entityDamager;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
