package com.areeoh.core.combat;

import com.areeoh.core.combat.listeners.CombatArmour;
import com.areeoh.core.combat.listeners.CombatDamage;
import com.areeoh.core.combat.listeners.CombatKnockback;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;

public class CombatManager extends Manager<Module> {

    public CombatManager(Plugin plugin) {
        super(plugin, "CombatManager");
    }

    @Override
    public void registerModules() {
        addModule(new CombatDamage(this));
        addModule(new CombatArmour(this));
        addModule(new CombatKnockback(this));
    }
}