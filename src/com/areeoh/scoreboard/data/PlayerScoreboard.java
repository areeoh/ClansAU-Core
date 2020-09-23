package com.areeoh.scoreboard.data;

import com.areeoh.framework.Module;
import com.areeoh.framework.updater.Update;
import com.areeoh.framework.updater.Updater;
import com.areeoh.scoreboard.ScoreboardManager;
import com.areeoh.scoreboard.ScoreboardPriority;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class PlayerScoreboard extends Module<ScoreboardManager> implements Comparable<PlayerScoreboard>, Listener, Updater {

    private final String header;
    private boolean shineDirection = true;
    private int shineIndex = 0;

    public PlayerScoreboard(ScoreboardManager manager, String moduleName, String header) {
        super(manager, moduleName);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public abstract void giveNewScoreboard(Player player);

    public abstract ScoreboardPriority getScoreboardPriority();

    @Update(ticks = 3)
    public void onUpdate() {
        List<PlayerScoreboard> modules = getManager().getModules(PlayerScoreboard.class);
        modules.sort(PlayerScoreboard::compareTo);
        for (PlayerScoreboard module : modules) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.getScoreboard().getObjective("info").setDisplayName(module.getDisplayName());
            }
            break;
        }
        this.shineIndex += 1;
        if (this.shineIndex == this.header.length() * 2) {
            this.shineIndex = 0;
            this.shineDirection = (!this.shineDirection);
        }
    }

    protected String getDisplayName() {
        String out;
        if (this.shineDirection) {
            out = ChatColor.GOLD + ChatColor.BOLD.toString();
        } else {
            out = ChatColor.WHITE + ChatColor.BOLD.toString();
        }
        for (int i = 0; i < this.header.length(); i++) {
            char c = this.header.charAt(i);
            if (this.shineDirection) {
                if (i == this.shineIndex) {
                    out = out + ChatColor.YELLOW + ChatColor.BOLD.toString();
                }
                if (i == this.shineIndex + 1) {
                    out = out + ChatColor.WHITE + ChatColor.BOLD.toString();
                }
            } else {
                if (i == this.shineIndex) {
                    out = out + ChatColor.YELLOW + ChatColor.BOLD.toString();
                }
                if (i == this.shineIndex + 1) {
                    out = out + ChatColor.GOLD + ChatColor.BOLD.toString();
                }
            }
            out = out + c;
        }
        return out;
    }

    protected String getBlank(int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(PlayerScoreboard o) {
        if (o.getScoreboardPriority().ordinal() > this.getScoreboardPriority().ordinal()) {
            return -1;
        }
        return 1;
    }
}