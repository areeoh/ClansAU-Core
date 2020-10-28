package com.areeoh.core.scoreboard.data;

import com.areeoh.core.client.Client;
import com.areeoh.core.client.ClientManager;
import com.areeoh.core.client.Rank;
import com.areeoh.core.client.events.ClientRankDemoteEvent;
import com.areeoh.core.client.events.ClientRankPromoteEvent;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.scoreboard.ScoreboardPriority;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class MainScoreboard extends PlayerScoreboard {

    public MainScoreboard(ScoreboardManager manager) {
        super(manager, "MainScoreboard", "ClansAU Hub");
    }

    @Override
    public void giveNewScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("info");
        if(objective == null) {
            objective = scoreboard.registerNewObjective("info", "dummy");
        }

        objective.setDisplayName(getDisplayName());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(getBlank(0)).setScore(15);
        objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Rank").setScore(14);
        Rank rank = getManager(ClientManager.class).getClient(player.getUniqueId()).getRank();
        objective.getScore(rank.getPrefix()).setScore(13);
        objective.getScore(getBlank(1)).setScore(12);
        objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Players Online").setScore(11);
        objective.getScore(Bukkit.getOnlinePlayers().stream().filter(player::canSee).count() + "").setScore(10);
        objective.getScore(getBlank(2)).setScore(9);

        player.setScoreboard(scoreboard);

        addPlayer(player);
    }

    @EventHandler
    public void onClientPromote(ClientRankPromoteEvent event) {
        final Player player = event.getClient().getPlayer();
        if(player == null) {
            return;
        }
        if(!ChatColor.stripColor(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName()).equals(getHeader())) {
            return;
        }
        updateRank(player);
        giveNewScoreboard(player);
    }

    @EventHandler
    public void onClientDemote(ClientRankDemoteEvent event) {
        final Player player = event.getClient().getPlayer();
        if(player == null) {
            return;
        }
        if(!ChatColor.stripColor(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName()).equals(getHeader())) {
            return;
        }
        updateRank(player);
        giveNewScoreboard(player);
    }

    @Override
    public ScoreboardPriority getScoreboardPriority() {
        return ScoreboardPriority.MONITOR;
    }

    private void addPlayer(Player player) {
        Client pClient = getManager(ClientManager.class).getClient(player.getUniqueId());

        for (Player online : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = online.getScoreboard();
            if(scoreboard.getTeam(pClient.getRank().getPrefix()) == null) {
                scoreboard.registerNewTeam(pClient.getRank().getPrefix());
            }
            scoreboard.getTeam(pClient.getRank().getPrefix()).setPrefix((pClient.getRank() == Rank.PLAYER ? "" : pClient.getRank().getRankColor(true) + " ") + ChatColor.YELLOW.toString());
            scoreboard.getTeam(pClient.getRank().getPrefix()).addPlayer(player);
        }
        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);
        for (Player online : Bukkit.getOnlinePlayers()) {
            Client client = getManager(ClientManager.class).getClient(online.getUniqueId());
            if (client.hasRank(Rank.MEDIA, false)) {
                updateRank(online);
                continue;
            }
            if(scoreboard.getTeam("Player") == null) {
                scoreboard.registerNewTeam("Player");
            }
            scoreboard.getTeam("Player").setPrefix(ChatColor.YELLOW + "");
            scoreboard.getTeam("Player").addPlayer(online);
        }
    }

    private void updateRank(Player player) {
        Client client = getManager(ClientManager.class).getClient(player.getUniqueId());
        for (Player online : Bukkit.getOnlinePlayers()) {
            String str = client.getRank().getPrefix();
            Scoreboard scoreboard = online.getScoreboard();
            if(scoreboard.getTeam(str) == null) {
                scoreboard.registerNewTeam(str);
                scoreboard.getTeam(str).setPrefix((client.getRank() == Rank.PLAYER ? "" : client.getRank().getRankColor(true) + " ") + ChatColor.YELLOW);
            }
            scoreboard.getTeam(str).addPlayer(player);
        }
    }
}