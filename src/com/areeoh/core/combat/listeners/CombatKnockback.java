package com.areeoh.core.combat.listeners;

import com.areeoh.core.combat.CombatManager;
import com.areeoh.core.combat.events.CustomDamageEvent;
import com.areeoh.core.framework.Primitive;
import com.areeoh.core.utility.UtilVelocity;
import com.areeoh.core.framework.Module;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class CombatKnockback extends Module<CombatManager> implements Listener {

    public CombatKnockback(CombatManager manager) {
        super(manager, "CombatKnockback");

        addPrimitive("SprintKnockbackAddition", new Primitive(3.0D, 3.0D));
    }

    //DEALS KNOCKBACK AFTER DAMAGE
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleKnockback(CustomDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.isKnockback() && event.getDamager() != null) {
            double knockback = Math.max(2.0D, event.getDamage());
            if (event.getDamager() instanceof Player) {
                final Player damager = (Player) event.getDamager();
                if (damager.isSprinting() && event.getDamageCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    knockback += getPrimitiveCasted(Double.class, "SprintKnockbackAddition");
                }
            }
            knockback = Math.log10(knockback);
            final Location knockbackSource = event.getDamager().getLocation();
            final Vector trajectory = UtilVelocity.getTrajectory2d(knockbackSource.toVector(), event.getDamagee().getLocation().toVector());
            trajectory.multiply((0.6 * knockback));
            trajectory.setY(Math.abs(trajectory.getY()));
            UtilVelocity.velocity(event.getDamagee(), trajectory, 0.3D + trajectory.length() * 0.8D, false, 0.0D, Math.abs(0.2D * knockback), 0.4D + 0.04D * knockback, true);
        }
    }
}
