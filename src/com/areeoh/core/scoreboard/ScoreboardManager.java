package com.areeoh.core.scoreboard;

import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.scoreboard.listeners.PlayerScoreboardHandler;
import com.areeoh.core.scoreboard.data.MainScoreboard;
import com.areeoh.core.scoreboard.listeners.TabListListener;
import org.bukkit.event.Listener;

public class ScoreboardManager extends Manager<Module> {

    public ScoreboardManager(Plugin plugin) {
        super(plugin, "ScoreboardManager");
    }

    @Override
    public void registerModules() {
        addModule(new TabListListener(this));
        addModule(new PlayerScoreboardHandler(this));

        addModule(new MainScoreboard(this));
    }
}
