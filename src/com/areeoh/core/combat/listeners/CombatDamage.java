package com.areeoh.core.combat.listeners;

import com.areeoh.core.combat.CombatManager;
import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.combat.events.CustomDeathEvent;
import com.areeoh.core.framework.Primitive;
import com.areeoh.core.utility.UtilItem;
import com.areeoh.core.framework.Module;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.WeakHashMap;

public class CombatDamage extends Module<CombatManager> implements Listener {

    private final WeakHashMap<LivingEntity, WeakHashMap<LivingEntity, Long>> hurtByMap = new WeakHashMap<>();
    private final WeakHashMap<LivingEntity, WeakHashMap<EntityDamageEvent.DamageCause, Long>> hurtByNonLivingMap = new WeakHashMap<>();

    public CombatDamage(CombatManager manager) {
        super(manager, "CombatListener");

        addPrimitive("ArrowDamageMultiplier", new Primitive(2.0D, 2.0D));

        addPrimitive("DefaultDamage", new Primitive(1.0D, 1.0D));
        addPrimitive("WoodBaseDamage", new Primitive(1.5D, 1.0D));
        addPrimitive("StoneBaseDamage", new Primitive(2.5D, 2.5D));
        addPrimitive("IronBaseDamage", new Primitive(4.5D, 4.5D));
        addPrimitive("GoldBaseDamage",  new Primitive(5.5D, 5.5D));
        addPrimitive("DiamondBaseDamage", new Primitive(5.0D, 5.0D));
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        final Entity damagee = event.getEntity();
        final LivingEntity damager = getDamager(event);
        final Projectile projectile = getProjectile(event);
        if(projectile instanceof FishHook) {
            return;
        }
        if (damager != null) {
            handleDamage(event, damager);
        }
        double damage = (float) event.getDamage();
        if (projectile instanceof Arrow) {
            damage = projectile.getVelocity().length() * getPrimitiveCasted(Double.class, "ArrowDamageMultiplier");
        }
        final CustomDamageEvent customDamageEvent = new CustomDamageEvent(damagee, damager, projectile, damage, event.getCause(), false, true, false);
        getPlugin().getServer().getPluginManager().callEvent(customDamageEvent);
        event.setCancelled(true);
    }

    //DEALS DAMAGE
    @EventHandler(priority = EventPriority.MONITOR)
    public void damage(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getDamagee() == null) {
            return;
        }
        if (!(event.getDamagee() instanceof LivingEntity)) {
            return;
        }
        final LivingEntity damagee = (LivingEntity) event.getDamagee();
        if (damagee.getHealth() <= 0.0D) {
            return;
        }
        if (event.getDamageCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getDamageCause() != EntityDamageEvent.DamageCause.PROJECTILE || event.getDamageCause() != EntityDamageEvent.DamageCause.CUSTOM) {
            damagee.getWorld().playSound(damagee.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
        }
        damagee.playEffect(EntityEffect.HURT);
        final EntityLiving entityDamagee = ((CraftLivingEntity) damagee).getHandle();
        EntityLiving entityDamager = null;
        if (event.getDamager() != null && event.getDamager() instanceof LivingEntity) {
            entityDamager = ((CraftLivingEntity) event.getDamager()).getHandle();
        }

        float newHealth = (float) (entityDamagee.getHealth() - event.getDamage());

        if(newHealth <= 0) {
            CustomDeathEvent customDeathEvent = new CustomDeathEvent(entityDamagee, entityDamager);
            Bukkit.getPluginManager().callEvent(customDeathEvent);
            if(customDeathEvent.isCancelled()) {
                return;
            }
        }

        entityDamagee.setHealth(newHealth);

        if (entityDamager != null) {
            if (entityDamager instanceof EntityHuman) {
                entityDamagee.killer = (EntityHuman) entityDamager;
            }
        }
        if (entityDamagee.getHealth() <= 0.0D) {
            if (entityDamager != null) {
                if (entityDamager instanceof EntityHuman) {
                    entityDamagee.die(DamageSource.playerAttack((EntityHuman) entityDamager));
                } else {
                    entityDamagee.die(DamageSource.mobAttack(entityDamager));
                }
            } else {
                entityDamagee.die(DamageSource.GENERIC);
            }
        }
        if(event.getProjectile() != null && event.getProjectile() instanceof Arrow) {
            if(event.getDamager() instanceof Player) {
                final Player player = (Player) event.getDamager();
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.5F, 0.5F);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void handleDamage(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Entity Entitydamagee = event.getDamagee();
        if (Entitydamagee instanceof LivingEntity) {
            if (((LivingEntity) Entitydamagee).getHealth() <= 0.0D) {
                event.setCancelled(true);
                return;
            }
            if (Entitydamagee instanceof Player) {
                final Player damagee = (Player) Entitydamagee;
                if (damagee.getGameMode() == GameMode.CREATIVE || damagee.getGameMode() == GameMode.SPECTATOR) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if (event.getDamager() != null) {
            if (event.getDamager() instanceof LivingEntity) {
                final LivingEntity damager = (LivingEntity) event.getDamager();
                if (!event.isIgnoreRate()) {
                    if (!canHurt((LivingEntity) event.getDamagee(), damager, event.getDamageDelay())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if(event.getDamager() == null) {
            if(!canHurtByNonLiving((LivingEntity) event.getDamagee(), event.getDamageCause())) {
                event.setCancelled(true);
            }
        }
    }

    //HANDLES THE DAMAGES FOR EACH ITEM
    private void handleDamage(EntityDamageEvent event, LivingEntity damager) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        final Material material = damager.getEquipment().getItemInHand().getType();
        if (!UtilItem.isWeapon(material)) {
            event.setDamage(getPrimitiveCasted(Double.class, "DefaultDamage"));
            return;
        }
        double damage = getPrimitiveCasted(Double.class, "DiamondBaseDamage");
        if (material.name().contains("WOOD")) {
            damage = getPrimitiveCasted(Double.class, "WoodBaseDamage");
        } else if (material.name().contains("STONE")) {
            damage = getPrimitiveCasted(Double.class, "StoneBaseDamage");
        } else if (material.name().contains("GOLD")) {
            damage = getPrimitiveCasted(Double.class, "GoldBaseDamage");
        } else if (material.name().contains("IRON")) {
            damage = getPrimitiveCasted(Double.class, "IronBaseDamage");
        }
        if (UtilItem.isAxe(material)) {
            damage--;
        }
        event.setDamage(damage);
    }

    private Projectile getProjectile(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return null;
        }
        EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
        if (entityEvent.getDamager() instanceof Projectile) {
            return (Projectile) entityEvent.getDamager();
        }
        return null;
    }

    private LivingEntity getDamager(EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent)) {
            return null;
        }
        final EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
        if (entityEvent.getDamager() instanceof LivingEntity) {
            return (LivingEntity) entityEvent.getDamager();
        }
        if (!(entityEvent.getDamager() instanceof Projectile)) {
            return null;
        }
        final Projectile projectile = (Projectile) entityEvent.getDamager();
        if (projectile.getShooter() == null) {
            return null;
        }
        if (!(projectile.getShooter() instanceof LivingEntity)) {
            return null;
        }
        return (LivingEntity) projectile.getShooter();
    }

    private boolean canHurt(LivingEntity damagee, LivingEntity damager, long damageDelay) {
        if (damagee == null) {
            return true;
        }
        if (!this.hurtByMap.containsKey(damager)) {
            this.hurtByMap.put(damager, new WeakHashMap<>());
            this.hurtByMap.get(damager).put(damagee, System.currentTimeMillis());
            return true;
        }
        if (!this.hurtByMap.get(damager).containsKey(damagee)) {
            this.hurtByMap.get(damager).put(damagee, System.currentTimeMillis());
            return true;
        }
        if (System.currentTimeMillis() - this.hurtByMap.get(damager).get(damagee) >= damageDelay) {
            this.hurtByMap.get(damager).put(damagee, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    private boolean canHurtByNonLiving(LivingEntity damagee, EntityDamageEvent.DamageCause damageCause) {
        if (damagee == null) {
            return true;
        }
        long delay = 600L;
        if (damageCause == EntityDamageEvent.DamageCause.POISON) {
            delay = 1000L;
        }
        if (!this.hurtByNonLivingMap.containsKey(damagee)) {
            this.hurtByNonLivingMap.put(damagee, new WeakHashMap<>());
            this.hurtByNonLivingMap.get(damagee).put(damageCause, System.currentTimeMillis());
            return true;
        }
        if (!this.hurtByNonLivingMap.get(damagee).containsKey(damageCause)) {
            this.hurtByNonLivingMap.get(damagee).put(damageCause, System.currentTimeMillis());
            return true;
        }
        if (System.currentTimeMillis() - this.hurtByNonLivingMap.get(damagee).get(damageCause) >= delay) {
            this.hurtByNonLivingMap.get(damagee).put(damageCause, System.currentTimeMillis());
            return true;
        }
        return false;
    }
}