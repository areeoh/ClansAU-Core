package com.areeoh.framework.commands;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.Rank;
import com.areeoh.framework.Module;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Command<T extends CommandSender> extends Module<CommandManager> {

    private String command; // command name
    private int index = 0; // position of command 0 = BASE COMMAND
    private int requiredArgs = 0;
    private String[] aliases;
    private List<Usage> usages;
    private Class<T> senderType; // Work on generic command class with auto cast
    private boolean hiddenCommand;
    private boolean consoleAllowed;
    private Rank requiredRank;
    private boolean isEnabled = true;

    public Command(CommandManager manager, String name, Class<T> senderType) {
        super(manager, name);

        this.command = "";
        this.senderType = senderType;

        requiredRank = Rank.PLAYER;
        this.aliases = new String[]{};
        usages = new ArrayList<>();
        hiddenCommand = false;
        consoleAllowed = false;
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (!isEnabled) {
            return false;
        }
        if (!senderType.isInstance(sender)) {
            return false;
        }
        if (senderType == Player.class) {
            final Client client = getManager().getManager(ClientManager.class).getClient(((Player) sender).getUniqueId());
            if(!client.hasRank(getRequiredRank(), true)) {
                return false;
            }
        }
        if (args.length < requiredArgs) {
            invalidArgsRequired(senderType.cast(sender));
            return false;
        }
        return this.execute(senderType.cast(sender), args);
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (!isEnabled) {
            return null;
        }
        if (!senderType.isInstance(sender)) {
            return null;
        }
        return this.tabComplete(senderType.cast(sender), args);
    }

    public abstract boolean execute(T sender, String[] args);

    public List<String> tabComplete(T sender, String[] args) {
        return null;
    }

    public String getCommand() {
        return command;
    }

    public int getIndex() {
        return index;
    }

    public int getRequiredArgs() {
        return requiredArgs;
    }

    public Class<?> getSenderType() {
        return this.senderType;
    }

    public String[] getAliases() {
        return aliases;
    }

    public List<Usage> getUsages() {
        return usages;
    }

    public boolean isHidden() {
        return hiddenCommand;
    }

    public boolean isConsoleAllowed() {
        return consoleAllowed;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public Command setCommand(String command) {
        this.command = command;
        return this;
    }

    public Command setIndex(int index) {
        this.index = index;
        return this;
    }

    public Command setRequiredArgs(int requiredArgs) {
        this.requiredArgs = requiredArgs;
        return this;
    }

    public Command setAliases(String... aliases) {
        this.aliases = aliases;
        return this;
    }

    public Command setHidden(boolean hiddenCommand) {
        this.hiddenCommand = hiddenCommand;
        return this;
    }

    public Command setConsoleAllowed(boolean consoleAllowed) {
        this.consoleAllowed = consoleAllowed;
        return this;
    }

    protected Command setRequiredRank(Rank requiredRank) {
        this.requiredRank = requiredRank;
        return this;
    }

    public void invalidArgsRequired(T sender) {
    }

    public Command addUsage(Usage usage) {
        usages.add(usage);
        return this;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
