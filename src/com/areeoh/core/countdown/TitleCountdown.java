package com.areeoh.core.countdown;

import com.areeoh.core.utility.UtilTime;
import com.areeoh.core.utility.UtilTitle;
import org.bukkit.entity.Player;

public abstract class TitleCountdown extends Countdown {

    private final String title, subtitle;

    public TitleCountdown(Player player, long timeLength, String title, String subtitle) {
        super(player, timeLength);

        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public void onUpdate() {
        double convert = UtilTime.convert((getSystemTime() - System.currentTimeMillis()) + getTimeLength(), UtilTime.TimeUnit.BEST, 1);

        UtilTitle.sendTitle(getPlayer(), title.replaceAll("%time%", String.valueOf(convert)), subtitle.replaceAll("%time%", String.valueOf(convert)), 0, 20, 0);
    }
}