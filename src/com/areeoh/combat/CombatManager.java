package com.areeoh.combat;

import com.areeoh.ClansAUCore;
import com.areeoh.combat.listeners.CombatArmour;
import com.areeoh.combat.listeners.CombatDamage;
import com.areeoh.combat.listeners.CombatKnockback;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;

public class CombatManager extends Manager<Module> {

    public CombatManager(ClansAUCore plugin) {
        super(plugin, "CombatManager");
    }

    @Override
    public void registerModules() {
        addModule(new CombatDamage(this));
        addModule(new CombatArmour(this));
        addModule(new CombatKnockback(this));
    }
}