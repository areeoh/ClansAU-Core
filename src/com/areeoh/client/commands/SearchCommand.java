package com.areeoh.client.commands;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.Rank;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import com.areeoh.framework.commands.Usage;
import com.areeoh.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SearchCommand extends Command<Player> {

    public SearchCommand(CommandManager manager) {
        super(manager, "SearchCommand", Player.class);
        setCommand("search");
        setIndex(1);
        setRequiredArgs(2);
        setRequiredRank(Rank.MOD);
        addUsage(new Usage("/client search <target>", "Displays information for a Client."));
    }

    @Override
    public boolean execute(Player player, String[] args) {
        final Client target = getManager(ClientManager.class).searchClient(player, args[1], true);
        if(target == null) {
            return false;
        }
        UtilMessage.message(player, "Client Search", ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " Information:");
        player.sendMessage(ChatColor.DARK_GREEN + "Profile: " + ChatColor.WHITE + "https://mine.ly/" + target.getName());
        player.sendMessage(ChatColor.DARK_GREEN + "Name: " + ChatColor.WHITE + target.getName());
        player.sendMessage(ChatColor.DARK_GREEN + "Previous Name: " + ChatColor.WHITE + target.getPreviousName());
        player.sendMessage(ChatColor.DARK_GREEN + "IP Address: " + ChatColor.WHITE + (target.getRank().ordinal() >= Rank.OWNER.ordinal() ? "N/A" : target.getIPAddress()));
        player.sendMessage(ChatColor.DARK_GREEN + "Rank: " + ChatColor.WHITE + target.getRank().getPrefix());
        player.sendMessage(ChatColor.DARK_GREEN + "Admin Mode: " + ChatColor.WHITE + (target.isAdministrating() ? "Enabled" : "Disabled"));
        UtilMessage.message(player, "Gamer", ChatColor.GREEN + ChatColor.UNDERLINE.toString() + "Click Here" + ChatColor.GRAY + " to display gamer information");
        return true;
    }

    @Override
    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Client", "You did not input a client to search.");
    }
}
