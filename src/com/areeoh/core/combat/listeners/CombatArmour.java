package com.areeoh.core.combat.listeners;

import com.areeoh.core.combat.CombatManager;
import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.framework.Primitive;
import com.areeoh.core.framework.Module;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CombatArmour extends Module<CombatManager> implements Listener {

    public CombatArmour(CombatManager manager) {
        super(manager, "CombatArmour");

        addPrimitive("LeatherHelmetReduction", new Primitive(6.0D, 6.0D));
        addPrimitive("LeatherChestplateReduction", new Primitive(14.0D, 14.0D));
        addPrimitive("LeatherLeggingsReduction", new Primitive(10.0D, 10.0D));
        addPrimitive("LeatherBootsReduction", new Primitive(6.0D, 6.0D));

        addPrimitive("ChainmailHelmetReduction", new Primitive(8.0D, 8.0D));
        addPrimitive("ChainmailChestplateReduction", new Primitive(20.0D, 20.0D));
        addPrimitive("ChainmailLeggingsReduction", new Primitive(16.0D, 16.0D));
        addPrimitive("ChainmailBootsReduction", new Primitive(1.8F, 1.8F));

        addPrimitive("IronHelmetReduction", new Primitive(8.0D, 8.0D));
        addPrimitive("IronChestplateReduction", new Primitive(24.0D, 24.0D));
        addPrimitive("IronLeggingsReduction", new Primitive(20.0D, 20.0D));
        addPrimitive("IronBootsReduction", new Primitive(8.0D, 8.0D));

        addPrimitive("GoldHelmetReduction", new Primitive(8.0D, 8.0D));
        addPrimitive("GoldChestplateReduction", new Primitive(24.0D, 24.0D));
        addPrimitive("GoldLeggingsReduction", new Primitive(20.0D, 20.0D));
        addPrimitive("GoldBootsReduction", new Primitive(8.0D, 8.0D));

        addPrimitive("DiamondHelmetReduction", new Primitive(12.0D, 12.0D));
        addPrimitive("DiamondChestplateReduction", new Primitive(26.0D, 26.0D));
        addPrimitive("DiamondLeggingsReduction", new Primitive(18.0D, 18.0D));
        addPrimitive("DiamondBootsReduction", new Primitive(12.0D, 12.0D));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void calculateArmour(CustomDamageEvent event) {
        if (event.isIgnoreArmor()) {
            return;
        }
        event.setDamage(getArmorPoints((float) event.getDamage(), event.getDamageeLivingEntity()));
    }

    private float getArmorPoints(float base, LivingEntity livingEntity) {
        if(!(livingEntity instanceof InventoryHolder)) {
            return base;
        }
        EntityEquipment inv = livingEntity.getEquipment();
        float reduction = 0.0F;
        for (ItemStack piece : inv.getArmorContents()) {
            if (piece != null && piece.getType() != Material.AIR) {
                reduction += (getReduction(piece));
            }
        }
        if(reduction == 0) {
            return base;
        }
        return base * (100 - reduction) / 100;
    }

    private double getReduction(ItemStack item) {
        return getPrimitiveCasted(Double.class, item.getType().name().replaceAll("_", "") + "Reduction");
    }
}
