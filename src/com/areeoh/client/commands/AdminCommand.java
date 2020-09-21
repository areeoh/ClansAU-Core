package com.areeoh.client.commands;

import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.Rank;
import com.areeoh.framework.commands.Command;
import com.areeoh.framework.commands.CommandManager;
import com.areeoh.framework.commands.Usage;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminCommand extends Command<Player> {

    public AdminCommand(CommandManager manager) {
        super(manager, "AdminCommand", Player.class);
        setCommand("admin");
        setIndex(1);
        setRequiredRank(Rank.ADMIN);
        addUsage(new Usage("/client admin", "Toggles Admin mode."));
    }

    @Override
    public boolean execute(Player player, String[] args) {
        final Client client = getManager(ClientManager.class).getClient(player.getUniqueId());
        client.setAdministrating(!client.isAdministrating());
        UtilMessage.message(player, "Client", "Admin Mode: " + (client.isAdministrating() ? (ChatColor.GREEN + "Enabled") : (ChatColor.RED + "Disabled")));
        Bukkit.getOnlinePlayers().stream().filter(p -> !p.getUniqueId().equals(player.getUniqueId())).filter(p -> getManager(ClientManager.class).getClient(p.getUniqueId()).hasRank(Rank.ADMIN, false)).forEach(p -> UtilMessage.message(p, "Client", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is " + (client.isAdministrating() ? "now Administrating." : "no longer Administrating.")));
        return true;
    }

    @Override
    public void invalidArgsRequired(Player sender) {
        UtilMessage.message(sender, "Client", "You did not input a client to search.");
    }
}