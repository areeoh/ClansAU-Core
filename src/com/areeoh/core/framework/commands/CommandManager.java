package com.areeoh.core.framework.commands;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.utility.UtilFormat;
import com.areeoh.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandManager extends Manager<Command> implements CommandExecutor, TabCompleter {

    public CommandManager(Plugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String cmdName, String[] args) {
        Command<?> command = getCommand(cmdName);
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (getCommand(arg) == null) {
                break;
            }
            command = getCommand(arg);
            if (command != null) {
                if (command.onCommand(sender, args)) {
                    return command.isHidden(); //TODO rethink this part as well as add failure message method?
                } else {
                    return true;
                }
            }
        }
        if (command != null) { // base command
            if (command.onCommand(sender, args)) {
                return command.isHidden();
            } else {
                if (!command.isEnabled()) {
                    UtilMessage.message(sender, "Module", ChatColor.GREEN + UtilFormat.cleanString(command.getCommand()) + " Command" + ChatColor.GRAY + " is currently disabled.");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command bCommand, String cmdName, String[] args) {
        Command<?> command = getCommand(cmdName);

        for (int i = 0; i < args.length; i++) {
            if (getCommand(args[i]) == null) {
                break;
            }
            command = getCommand(args[i]);
            if (command != null) {
                return command.onTabComplete(sender, args);
            }
        }

        if (command != null) { // base command
            return command.onTabComplete(sender, args);
        }
        return null;
    }

    public Command getBaseCommand() {
        for (Command command : getModules()) {
            if (command.getIndex() == 0) {
                return command;
            }
        }
        return null;
    }

    public Command getCommand(String input) {
        for (Command command : getModules()) {
            if (command.getCommand().equalsIgnoreCase(input) || Arrays.asList(command.getAliases()).contains(input.toLowerCase())) {
                return command;
            }
        }
        return null;
    }

    @Override
    public void initialize(Plugin javaPlugin) {
        super.initialize(javaPlugin);

        javaPlugin.getCommand(getBaseCommand().getCommand()).setExecutor(this);
        javaPlugin.getCommand(getBaseCommand().getCommand()).setTabCompleter(this);
    }

    @Override
    public void shutdown() {
        super.shutdown();

        getPlugin().getCommand(getBaseCommand().getCommand()).setExecutor(null);
        getPlugin().getCommand(getBaseCommand().getCommand()).setTabCompleter(null);
    }
}