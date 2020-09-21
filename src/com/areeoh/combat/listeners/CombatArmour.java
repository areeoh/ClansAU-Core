package com.areeoh.combat.listeners;

import com.areeoh.combat.CombatManager;
import com.areeoh.combat.events.CustomDamageEvent;
import com.areeoh.framework.Module;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class CombatArmour extends Module<CombatManager> implements Listener {

    public CombatArmour(CombatManager manager) {
        super(manager, "CombatArmour");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void calculateArmour(CustomDamageEvent event) {
        if (event.isIgnoreArmor()) {
            return;
        }
        event.setDamage(getArmorPoints((float) event.getDamage(), event.getDamageeLivingEntity()));
    }

    private float getArmorPoints(float base, LivingEntity livingEntity) {
        EntityEquipment inv = livingEntity.getEquipment();
        float reduction = 0.0F;
        for (ItemStack piece : inv.getArmorContents()) {
            if (piece != null) {
                reduction += (getReduction(piece));
            }
        }
        if(reduction == 0) {
            return base;
        }
        return base * (100 - reduction) / 100;
    }

    private float getReduction(ItemStack item) {
        float red = 0;
        Material piece = item.getType();

        switch(piece){
            case LEATHER_HELMET: red += 6.0; break;
            case LEATHER_CHESTPLATE: red += 14.0; break;
            case LEATHER_LEGGINGS: red += 10.0; break;
            case LEATHER_BOOTS: red += 6.0; break;

            case CHAINMAIL_HELMET: red += 8.0; break;
            case CHAINMAIL_CHESTPLATE: red += 20.0; break;
            case CHAINMAIL_LEGGINGS: red += 16.0; break;
            case CHAINMAIL_BOOTS: red += 1.8; break;

            case IRON_HELMET: red += 8.0; break;
            case IRON_CHESTPLATE: red += 24.0; break;
            case IRON_LEGGINGS: red += 20.0; break;
            case IRON_BOOTS: red += 8.0; break;

            case GOLD_HELMET: red += 8.0; break;
            case GOLD_CHESTPLATE: red += 24.0; break;
            case GOLD_LEGGINGS: red += 20.0; break;
            case GOLD_BOOTS: red += 8.0; break;

            case DIAMOND_HELMET: red += 12.0; break;
            case DIAMOND_CHESTPLATE: red += 26.0; break;
            case DIAMOND_LEGGINGS: red += 18.0; break;
            case DIAMOND_BOOTS: red += 12.0; break;
        }
        return red;
    }

}
