package com.areeoh.core.recharge;

public class RechargeData {
    private final String name;
    private final long time;
    private long rechargeTime;
    private boolean inform;
    private boolean countdown;

    public RechargeData(String name, boolean inform, boolean countdown, long rechargeTime) {
        this.countdown = countdown;
        this.name = name;
        this.inform = inform;
        this.time = System.currentTimeMillis();
        this.rechargeTime = rechargeTime;
    }

    public long getTime() {
        return time;
    }

    public long getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(long rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getName() {
        return name;
    }

    public boolean isInform() {
        return inform;
    }

    public void setInform(boolean inform) {
        this.inform = inform;
    }

    public boolean isCountdown() {
        return countdown;
    }

    public void setCountdown(boolean countdown) {
        this.countdown = countdown;
    }

    public long getRemaining() {
        return (rechargeTime + time - System.currentTimeMillis());
    }
}