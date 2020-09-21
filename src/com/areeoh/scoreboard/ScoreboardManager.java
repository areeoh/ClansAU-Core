package com.areeoh.scoreboard;

import com.areeoh.ClansAUCore;
import com.areeoh.client.Client;
import com.areeoh.client.ClientManager;
import com.areeoh.client.Rank;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import com.areeoh.scoreboard.listeners.ScoreboardRankListener;
import com.areeoh.scoreboard.listeners.TabListListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager extends Manager<Module> implements Listener {

    public HashMap<UUID, Scoreboard> scoreboardMap;
    private final String noneTeamName = "None@";
    private final String rankAppend = "@";

    public ScoreboardManager(ClansAUCore plugin) {
        super(plugin, "ScoreboardManager");
        this.scoreboardMap = new HashMap<>();
    }

    @Override
    public void registerModules() {
        addModule(new TabListListener(this));
        addModule(new ScoreboardRankListener(this));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        assignScoreboard(player);
        addNewPlayer(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        scoreboardMap.remove(event.getPlayer().getUniqueId());
    }

    public void assignScoreboard(Player player) {
        final Scoreboard scoreboard = getScoreboard(player);
        final Objective objective = scoreboard.registerNewObjective("info", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        //objective.setDisplayName(getModule(SideBarHandler.class).getHeader());
        //getModule(SideBarHandler.class).updateSideBar(player);
    }

    public void updateRank(Player player) {
        //if (getManager(ClanManager.class).getClan(player.getUniqueId()) != null) {
            //return;
        //}
        final Client client = getManager(ClientManager.class).getClient(player.getUniqueId());
        if (client.hasRank(Rank.MEDIA, false)) {
            for(Scoreboard s : scoreboardMap.values()) { //FIXED THIS NEED TO CHECK
                String str = getTrimmedRankPrefix(client.getRank());
                if (s.getTeam(str) == null) {
                    s.registerNewTeam(str);
                    s.getTeam(str).setPrefix(client.getRank().getRankColor(true) + " " + ChatColor.YELLOW);
                }
                s.getTeam(str).addPlayer(player);
            }
        } else {
            for (Scoreboard s : scoreboardMap.values()) {
                if (s.getTeam(noneTeamName) == null) {
                    s.registerNewTeam(noneTeamName);
                    s.getTeam(noneTeamName).setPrefix(ChatColor.YELLOW.toString());
                }
                s.getTeam(noneTeamName).addPlayer(player);
            }
        }
    }

    public void addNewPlayer(Player player) {
        for (Map.Entry<UUID, Scoreboard> entry : getManager(ScoreboardManager.class).getScoreboardMap().entrySet()) {
            if(entry.getValue().getTeam(getNoneTeamName()) == null) {
                entry.getValue().registerNewTeam(getNoneTeamName());
            }
            entry.getValue().getTeam(getNoneTeamName()).setPrefix(ChatColor.YELLOW + "");
            entry.getValue().getTeam(getNoneTeamName()).addPlayer(player);
        }
        final Scoreboard scoreboard = getScoreboard(player);
        scoreboard.getTeams().forEach(Team::unregister);
        for (Player online : Bukkit.getOnlinePlayers()) {
            final Client client = getManager(ClientManager.class).getClient(online.getUniqueId());
            if(client != null) {
                if (client.hasRank(Rank.MEDIA, false)) {
                    updateRank(online);
                    continue;
                }
            }
            if(scoreboard.getTeam(getNoneTeamName()) == null) {
                scoreboard.registerNewTeam(getNoneTeamName());
            }
            scoreboard.getTeam(getNoneTeamName()).setPrefix(ChatColor.YELLOW + "");
            scoreboard.getTeam(getNoneTeamName()).addPlayer(online);
        }
    }

    public HashMap<UUID, Scoreboard> getScoreboardMap() {
        return scoreboardMap;
    }

    public Scoreboard getScoreboard(Player player) {
        if(getScoreboardMap().containsKey(player.getUniqueId())) {
            return getScoreboardMap().get(player.getUniqueId());
        }
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        getScoreboardMap().put(player.getUniqueId(), scoreboard);
        return scoreboard;
    }

    private String getTrimmedRankPrefix(Rank rank) {
        final String str = rank.name() + rankAppend;
        return str.length() > 16 ? str.substring(0, 15) : str;
    }

    public String getNoneTeamName() {
        return noneTeamName;
    }

    public String getRankAppend() {
        return rankAppend;
    }
}
