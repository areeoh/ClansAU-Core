package com.areeoh.client.commands;

import com.areeoh.ClansAUCore;
import com.areeoh.client.Rank;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import org.bukkit.entity.Player;

public class ClientCmdManager extends CommandManager {

    public ClientCmdManager(ClansAUCore plugin) {
        super(plugin, "ClientCommandManager");
    }

    @Override
    public void registerModules() {
        addModule(new BaseCommand(this));
        addModule(new SearchCommand(this));
        addModule(new AdminCommand(this));
        addModule(new PromoteCommand(this));
        addModule(new DemoteCommand(this));
    }

    class BaseCommand extends Command<Player> {
        public BaseCommand(CommandManager manager) {
            super(manager, "BaseCommand", Player.class);
            setCommand("client");
            setIndex(0);
            setRequiredArgs(1);
            setRequiredRank(Rank.MOD);
        }

        @Override
        public boolean execute(Player player, String[] args) {
            return true;
        }
    }
}
