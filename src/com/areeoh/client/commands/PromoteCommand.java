package com.areeoh.client.commands;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.ClientRepository;
import com.areeoh.client.Rank;
import com.areeoh.client.events.ClientRankPromoteEvent;
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

public class PromoteCommand extends Command<CommandSender> {

    public PromoteCommand(CommandManager manager) {
        super(manager, "PromoteCommand", CommandSender.class);
        setCommand("promote");
        setIndex(1);
        setRequiredArgs(2);
        setRequiredRank(Rank.OWNER);
        addUsage(new Usage("/client promote <target>", "Promote a Client."));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        final Client target = getManager(ClientManager.class).searchClient(sender, args[1], true);
        if(target == null) {
            return false;
        }
        if(!sender.isOp()) {
            if(target.getUUID().equals(((Player)sender).getUniqueId())) {
                UtilMessage.message(sender, "Client", "You cannot promote yourself.");
                return false;
            }
            if(getManager(ClientManager.class).getClient(((Player) sender).getUniqueId()).hasRank(target.getRank(), false)) {
                UtilMessage.message(sender, "Client", ChatColor.GRAY + "You do not outrank " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                return false;
            }
        }
        if(target.getRank() == Rank.DEVELOPER) {
            UtilMessage.message(sender, "Client", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " cannot be promoted any further.");
            return false;
        }
        target.setRank(Rank.values()[target.getRank().ordinal() + 1]);
        getManager(DatabaseManager.class).getModule(ClientRepository.class).updateRank(target);
        UtilMessage.broadcast("Client", ChatColor.YELLOW + (sender instanceof ConsoleCommandSender ? UtilFormat.cleanString(sender.getName()) : sender.getName()) + ChatColor.GRAY + " promoted " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + target.getRank().getRankColor(true) + ChatColor.GRAY + ".");
        Bukkit.getServer().getPluginManager().callEvent(new ClientRankPromoteEvent(sender, target));
        return true;
    }

    @Override
    public void invalidArgsRequired(CommandSender sender) {
        UtilMessage.message(sender, "Client", "You did not input a client to promote.");
    }
}
