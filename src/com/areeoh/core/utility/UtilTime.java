package com.areeoh.core.utility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilTime {

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat formatterTime = new SimpleDateFormat("dd/MM/yyyy H:m");

    public static String getDate(long time) {
        return formatter.format(new Date(time));
    }

    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    /**
     * Checks if the current time has passed the given time
     *
     * @param futureTime
     * @return boolean
     */
    public static boolean hasEnoughTimePassed(long futureTime) {
        return futureTime - System.currentTimeMillis() <= 0;
    }

    public static double ticksToSeconds(int ticks) {
        return (double) ticks / 20;
    }

    public static long ticksToMillis(int ticks) {
        return (long) (ticksToSeconds(ticks) * 1000);
    }

    public static int secondsToTicks(float seconds) {
        return (int) (seconds * 20.0);
    }

    public static int secondsToMillis(float seconds) {
        return (int) seconds * 1000;
    }

    public static long addSeconds(double seconds) {
        return (long) (System.currentTimeMillis() + (seconds * 1000));
    }

    public static double trim(double untrimmed, double d) {
        String format = "#.#";

        for (int i = 1; i < d; i++) {
            format = format + "#";
        }
        DecimalFormat twoDec = new DecimalFormat(format);
        return Double.parseDouble(twoDec.format(untrimmed));
    }

    public static double convert(double d, TimeUnit unit, int decPoint) {
        if (unit == TimeUnit.BEST) {
            if (d < 60000L) {
                unit = TimeUnit.SECONDS;
            } else if (d < 3600000L) {
                unit = TimeUnit.MINUTES;
            } else if (d < 86400000L) {
                unit = TimeUnit.HOURS;
            } else if (d < 31536000000L) {
                unit = TimeUnit.DAYS;
            } else {
                unit = TimeUnit.YEARS;
            }
        }
        if (unit == TimeUnit.SECONDS) {
            return trim(d / 1000.0D, decPoint);
        }
        if (unit == TimeUnit.MINUTES) {
            return trim(d / 60000.0D, decPoint);
        }
        if (unit == TimeUnit.HOURS) {
            return trim(d / 3600000.0D, decPoint);
        }
        if (unit == TimeUnit.DAYS) {
            return trim(d / 86400000, decPoint);
        }
        if (unit == TimeUnit.YEARS) {
            return trim(d / 31536000000.0D, decPoint);
        }
        return trim(d, decPoint);
    }

    public static String getTimeUnit2(double d) {
        if (d < 60000L) {
            return "Seconds";
        } else if (d < 3600000L) {
            return "Minutes";
        } else if (d < 86400000L) {
            return "Hours";
        } else if (d < 31536000000L) {
            return "Days";
        } else {
            return "Years";
        }
    }

    public static String getTimeUnit(TimeUnit unit) {
        switch (unit) {
            case SECONDS:
                return "Seconds";
            case MINUTES:
                return "Minutes";
            case HOURS:
                return "Hours";
            case DAYS:
                return "Days";
            case YEARS:
                return "Years";

        }
        return "";
    }


    public static String getTime(double d, TimeUnit unit, int decPoint) {
        return UtilTime.convert(d, TimeUnit.BEST, decPoint) + " " + UtilTime.getTimeUnit2(d);
    }

    public static String getTime2(double d, TimeUnit unit, int decPoint) {
        return UtilTime.convert(d, unit, decPoint) + " " + UtilTime.getTimeUnit(unit);
    }

    public enum TimeUnit {
        BEST, YEARS, DAYS, HOURS, MINUTES, SECONDS,
    }
}
