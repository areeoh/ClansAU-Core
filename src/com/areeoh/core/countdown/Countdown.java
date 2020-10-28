package com.areeoh.core.countdown;

import org.bukkit.entity.Player;

public abstract class Countdown {

    private final Player player;
    private final long systemTime;
    private long timeLength;

    public Countdown(Player player, long timeLength) {
        this.player = player;
        this.systemTime = System.currentTimeMillis();
        this.timeLength = timeLength;
    }

    public abstract void onUpdate();

    public abstract void finish();

    public Player getPlayer() {
        return player;
    }

    public long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(long timeLength) {
        this.timeLength = timeLength;
    }

    public long getSystemTime() {
        return systemTime;
    }
}