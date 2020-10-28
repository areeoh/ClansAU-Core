package com.areeoh.core.countdown.listeners;

import com.areeoh.core.countdown.Countdown;
import com.areeoh.core.countdown.CountdownManager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.updater.Update;
import com.areeoh.core.framework.updater.Updater;

import java.util.Iterator;

public class CountdownListener extends Module<CountdownManager> implements Updater {

    public CountdownListener(CountdownManager manager) {
        super(manager, "CountdownListener");
    }

    @Update(ticks = 0)
    public void onUpdate() {
        for (Iterator<Countdown> it = getManager().getCountdowns().iterator(); it.hasNext(); ) {
            Countdown countdown = it.next();
            if(((countdown.getSystemTime() - System.currentTimeMillis()) + countdown.getTimeLength()) <= 0) {
                countdown.finish();
                it.remove();
                continue;
            }
            countdown.onUpdate();
        }
    }
}
