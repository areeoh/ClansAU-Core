package com.areeoh.scoreboard.listeners;

import com.areeoh.framework.Module;
import com.areeoh.framework.updater.Updater;
import com.areeoh.scoreboard.ScoreboardManager;
import com.areeoh.scoreboard.data.PlayerScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerScoreboardHandler extends Module<ScoreboardManager> implements Updater, Listener {

    public PlayerScoreboardHandler(ScoreboardManager manager) {
        super(manager, "PlayerScoreboardHandler");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<PlayerScoreboard> modules = getManager().getModules(PlayerScoreboard.class);
        modules.sort(PlayerScoreboard::compareTo);
        for (PlayerScoreboard module : modules) {
            module.giveNewScoreboard(event.getPlayer());
            break;
        }
    }
}