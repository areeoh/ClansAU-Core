package com.areeoh.core.client.commands;

import com.areeoh.core.ClansAUCore;
import com.areeoh.core.client.Client;
import com.areeoh.core.client.ClientManager;
import com.areeoh.core.client.ClientRepository;
import com.areeoh.core.client.Rank;
import com.areeoh.core.client.events.ClientRankDemoteEvent;
import com.areeoh.core.client.events.ClientRankPromoteEvent;
import com.areeoh.core.database.DatabaseManager;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.framework.commands.Usage;
import com.areeoh.core.utility.UtilFormat;
import com.areeoh.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

    class AdminCommand extends Command<Player> {

        public AdminCommand(CommandManager manager) {
            super(manager, "AdminCommand", Player.class);
            setCommand("admin");
            setIndex(1);
            setRequiredRank(Rank.ADMIN);
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

    class SearchCommand extends Command<Player> {

        public SearchCommand(CommandManager manager) {
            super(manager, "SearchCommand", Player.class);
            setCommand("search");
            setIndex(1);
            setRequiredArgs(2);
            setRequiredRank(Rank.MOD);
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

    class DemoteCommand extends Command<CommandSender> {

        public DemoteCommand(CommandManager manager) {
            super(manager, "DemoteCommand", CommandSender.class);
            setCommand("demote");
            setIndex(1);
            setRequiredArgs(2);
            setRequiredRank(Rank.OWNER);
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
    }

    class PromoteCommand extends Command<CommandSender> {

        public PromoteCommand(CommandManager manager) {
            super(manager, "PromoteCommand", CommandSender.class);
            setCommand("promote");
            setIndex(1);
            setRequiredArgs(2);
            setRequiredRank(Rank.OWNER);
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
}
