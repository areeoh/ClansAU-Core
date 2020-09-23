package com.areeoh.scoreboard;

import com.areeoh.ClansAUCore;
import com.areeoh.framework.Manager;
import com.areeoh.framework.Module;
import com.areeoh.scoreboard.data.MainScoreboard;
import com.areeoh.scoreboard.listeners.PlayerScoreboardHandler;
import com.areeoh.scoreboard.listeners.TabListListener;
import org.bukkit.event.Listener;

public class ScoreboardManager extends Manager<Module> implements Listener {

    public ScoreboardManager(ClansAUCore plugin) {
        super(plugin, "ScoreboardManager");
    }

    @Override
    public void registerModules() {
        addModule(new TabListListener(this));
        addModule(new PlayerScoreboardHandler(this));

        addModule(new MainScoreboard(this));
    }
}
