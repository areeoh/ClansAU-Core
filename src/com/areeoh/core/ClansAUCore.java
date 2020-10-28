package com.areeoh.core;

import com.areeoh.core.blockregen.BlockRegenManager;
import com.areeoh.core.bungee.BungeeHandler;
import com.areeoh.core.client.ClientManager;
import com.areeoh.core.client.commands.ClientCmdManager;
import com.areeoh.core.client.commands.GamemodeCmdManager;
import com.areeoh.core.client.commands.ModuleCmdManager;
import com.areeoh.core.client.commands.PrimitiveCmdManager;
import com.areeoh.core.combat.CombatManager;
import com.areeoh.core.config.ConfigManager;
import com.areeoh.core.countdown.CountdownManager;
import com.areeoh.core.database.DatabaseManager;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.updater.UpdateManager;
import com.areeoh.core.item.ItemManager;
import com.areeoh.core.menu.MenuManager;
import com.areeoh.core.recharge.RechargeManager;
import com.areeoh.core.scoreboard.ScoreboardManager;

import java.util.Set;

public class ClansAUCore extends Plugin {

    @Override
    public void onEnable() {
        registerManagers();
    }

    @Override
    public void onDisable() {
        for (Manager manager : getManagers()) {
            manager.shutdown();
        }
    }

    private void registerManagers() {
        addManager(new UpdateManager(this));

        addManager(new ClientManager(this));
        addManager(new DatabaseManager(this));
        addManager(new ScoreboardManager(this));

        addManager(new CombatManager(this));
        addManager(new ItemManager(this));
        addManager(new MenuManager(this));

        addManager(new CountdownManager(this));
        addManager(new RechargeManager(this));
        addManager(new BungeeHandler(this));
        addManager(new BlockRegenManager(this));

        addManager(new ModuleCmdManager(this));
        addManager(new PrimitiveCmdManager(this));
        addManager(new ClientCmdManager(this));
        addManager(new GamemodeCmdManager(this));
        addManager(new ConfigManager(this));
    }

    @Override
    public Set<Manager> getManagers() {
        return managers;
    }
}