package com.areeoh.client.commands;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.ClientRepository;
import com.areeoh.client.Rank;
import com.areeoh.client.events.ClientRankDemoteEvent;
import com.areeoh.database.DatabaseManager;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import com.areeoh.framework.commands.Usage;
import com.areeoh.utility.UtilFormat;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DemoteCommand extends Command<CommandSender> {

    public DemoteCommand(CommandManager manager) {
        super(manager, "DemoteCommand", CommandSender.class);
        setCommand("demote");
        setIndex(1);
        setRequiredArgs(2);
        setRequiredRank(Rank.OWNER);
        addUsage(new Usage("/client demote <target>", "Demote a Client."));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        final Client target = getManager(ClientManager.class).searchClient(sender, args[1], true);
        if (target == null) {
            return false;
        }
        if (!sender.isOp() && target.getUUID().equals(((Player) sender).getUniqueId())) {
            UtilMessage.message(sender, "Client", "You cannot demote yourself.");
            return false;
        }
        if (target.getRank() == Rank.PLAYER) {
            UtilMessage.message(sender, "Client", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " cannot be demoted any further.");
            return false;
        }
        target.setRank(Rank.values()[target.getRank().ordinal() - 1]);
        getManager(DatabaseManager.class).getModule(ClientRepository.class).updateRank(target);
        UtilMessage.broadcast("Client", ChatColor.YELLOW + (sender instanceof ConsoleCommandSender ? UtilFormat.cleanString(sender.getName()) : sender.getName()) + ChatColor.GRAY + " demoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + target.getRank().getRankColor(true) + ChatColor.GRAY + ".");
        Bukkit.getPluginManager().callEvent(new ClientRankDemoteEvent(sender, target));
        return true;
    }

    @Override
    public void invalidArgsRequired(CommandSender sender) {
        UtilMessage.message(sender, "Client", "You did not input a client to demote.");
    }
}