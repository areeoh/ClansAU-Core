package com.areeoh.core.client.commands;

import com.areeoh.core.ClansAUCore;
import com.areeoh.core.client.Rank;
import com.areeoh.core.framework.Frame;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleCmdManager extends CommandManager {

    public ModuleCmdManager(ClansAUCore plugin) {
        super(plugin, "ModuleCommand");
    }

    @Override
    public void registerModules() {
        addModule(new Command<CommandSender>(this, "ModuleBase", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                UtilMessage.message(sender, "Module", "/module toggle <manager> <module>");
                return false;
            }
            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                return Arrays.asList("toggle", "help");
            }
        }.setCommand("module").setRequiredRank(Rank.OWNER));

        addModule(new Command<CommandSender>(this, "ModuleToggle", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                String targetManager = args[1];
                String targetModule = args[2];
                Manager manager = getManager(targetManager);
                if (manager != null) {
                    Module module = manager.getModule(targetModule);
                    if (module != null) {
                        UtilMessage.message(sender, "Module", module.getName() + ": " + (module.isEnabled() ? ChatColor.RED + "Disabled" : ChatColor.GREEN + "Enabled"));
                        if (module.isEnabled())
                            module.shutdown();
                        else
                            module.initialize(getPlugin());
                        return true;
                    }
                }
                return false;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                if(args.length == 2) {
                    return getPlugin().getManagers().stream().filter(manager -> manager.getName().toLowerCase().startsWith(args[1].toLowerCase())).map(Frame::getName).collect(Collectors.toList());
                }
                return (List<String>) getPlugin().getManager(args[1].toLowerCase()).getModules().stream().filter(o -> o instanceof Module).map(o -> ((Module) o).getName()).collect(Collectors.toList());
            }
        }.setCommand("toggle").setRequiredRank(Rank.OWNER).setIndex(1).setRequiredArgs(3).setAliases("tg"));

    }
}
