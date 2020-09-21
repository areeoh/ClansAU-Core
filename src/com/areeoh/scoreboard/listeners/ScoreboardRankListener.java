package com.areeoh.scoreboard.listeners;

import com.areeoh.client.events.ClientRankDemoteEvent;
import com.areeoh.client.events.ClientRankPromoteEvent;
import com.areeoh.framework.Module;
import com.areeoh.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ScoreboardRankListener extends Module<ScoreboardManager> implements Listener {
    public ScoreboardRankListener(ScoreboardManager manager) {
        super(manager, "ScoreboardRankListener");
    }

    @EventHandler
    public void onClientPromote(ClientRankPromoteEvent event) {
        final Player player = event.getClient().getPlayer();
        if(player == null) {
            return;
        }
        getManager(ScoreboardManager.class).updateRank(player);
    }

    @EventHandler
    public void onClientDemote(ClientRankDemoteEvent event) {
        final Player player = event.getClient().getPlayer();
        if(player == null) {
            return;
        }
        getManager(ScoreboardManager.class).updateRank(player);
    }
}