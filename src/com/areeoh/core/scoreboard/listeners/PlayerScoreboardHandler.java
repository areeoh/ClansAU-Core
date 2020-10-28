package com.areeoh.core.scoreboard.listeners;

import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.scoreboard.ScoreboardManager;
import com.areeoh.core.scoreboard.data.PlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerScoreboardHandler extends Module<ScoreboardManager> implements Listener {

    public PlayerScoreboardHandler(ScoreboardManager manager) {
        super(manager, "PlayerScoreboardHandler");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        List<PlayerScoreboard> modules = getManager().getModules(PlayerScoreboard.class);
        modules.forEach(playerScoreboard -> System.out.println(playerScoreboard.getName()));
        modules.sort(PlayerScoreboard::compareTo);
        for (PlayerScoreboard module : modules) {
            module.giveNewScoreboard(event.getPlayer());
            break;
        }
    }
}