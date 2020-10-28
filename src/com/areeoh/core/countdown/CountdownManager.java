package com.areeoh.core.countdown;

import com.areeoh.core.countdown.listeners.CountdownListener;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.updater.Updater;

import java.util.HashSet;
import java.util.Set;

public class CountdownManager extends Manager<Module> implements Updater {

    private final Set<Countdown> countdowns = new HashSet<>();

    public CountdownManager(Plugin plugin) {
        super(plugin, "CountdownManager");
    }

    @Override
    public void registerModules() {
        addModule(new CountdownListener(this));
    }

    public void addCountdown(Countdown countdown) {
        countdowns.add(countdown);
    }

    public Set<Countdown> getCountdowns() {
        return countdowns;
    }
}