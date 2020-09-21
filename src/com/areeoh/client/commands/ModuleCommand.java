package com.areeoh.client.commands;

import com.areeoh.ClansAUCore;
import com.areeoh.framework.Frame;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import com.areeoh.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleCommand extends CommandManager {

    public ModuleCommand(ClansAUCore plugin) {
        super(plugin, "ModuleCommand");
    }

    @Override
    public void registerModules() {
        addModule(new Command<CommandSender>(this, "ModuleBase", CommandSender.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                UtilMessage.message(sender, "Module", "/module toggle <module>");
                return false;
            }
            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                return Arrays.asList("toggle", "help");
            }
        }.setCommand("module"));

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
        }.setCommand("toggle").setIndex(1).setRequiredArgs(3).setAliases("tg"));

    }
}
