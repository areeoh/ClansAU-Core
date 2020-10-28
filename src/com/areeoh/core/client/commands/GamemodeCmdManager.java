package com.areeoh.core.client.commands;

import com.areeoh.core.client.Rank;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.commands.Command;
import com.areeoh.core.framework.commands.CommandManager;
import com.areeoh.core.framework.commands.Usage;
import com.areeoh.core.utility.UtilFormat;
import com.areeoh.core.utility.UtilJava;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.core.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCmdManager extends CommandManager {

    public GamemodeCmdManager(Plugin plugin) {
        super(plugin, "GamemodeCmdManager");
    }

    @Override
    public void registerModules() {
        addModule(new Command(this, "GamemodeCommand", Player.class) {
            @Override
            public boolean execute(CommandSender sender, String[] args) {
                Player player = (Player) sender;
                ArrayList<GameMode> gameModes = new ArrayList<>(Arrays.asList(GameMode.values()));

                GameMode gameMode = UtilJava.searchList(gameModes, t -> t.name().toLowerCase().contains(args[0].toLowerCase()));
                if (gameMode == null) {
                    UtilMessage.message(sender, "Gamemode Search", ChatColor.YELLOW + "" + gameModes.size() + ChatColor.GRAY + " Matches found [" + ChatColor.YELLOW + gameModes.stream().map(gm -> UtilFormat.cleanString(gm.name())).collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.YELLOW)) + ChatColor.GRAY + "]");
                    return false;
                }
                if (args.length < 2) {
                    player.setGameMode(gameMode);
                    UtilMessage.message(player, "Gamemode", "You set your gamemode to " + ChatColor.GREEN + UtilFormat.cleanString(gameMode.name()) + ChatColor.GRAY + ".");
                } else {
                    Player target = UtilPlayer.searchPlayer(player, args[1], true);
                    if (target == null) {
                        return false;
                    }
                    target.setGameMode(gameMode);
                    UtilMessage.message(player, "Gamemode", "You set " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + "'s gamemode to " + ChatColor.GREEN + UtilFormat.cleanString(gameMode.name()) + ChatColor.GRAY + ".");
                }
                return true;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String[] args) {
                List<String> list = new ArrayList<>();
                if (args.length == 1) {
                    for (GameMode value : GameMode.values()) {
                        if (value.name().toLowerCase().contains(args[0].toLowerCase())) {
                            list.add(value.name());
                        }
                    }
                }
                return list;
            }
        }.setCommand("gamemode").setAliases("gm").setRequiredRank(Rank.OWNER).setRequiredArgs(1).setIndex(0));
    }
}