package com.areeoh.framework.updater;

import com.areeoh.utility.UtilTime;

import java.lang.reflect.Method;

public class UpdateTask {

    private Object object;
    private Method method;
    private int ticks;
    private long nextUpdate;
    private boolean async;

    private boolean running;

    public UpdateTask(Object object, Method method, int ticks, boolean async) {
        this.object = object;
        this.method = method;
        this.ticks = ticks;
        this.async = async;
    }

    public void run(long lastTick) {
        if (UtilTime.hasEnoughTimePassed(nextUpdate)) {
            try {
                method.invoke(object, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            nextUpdate = lastTick + UtilTime.ticksToMillis(ticks);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public long getNextUpdate() {
        return nextUpdate;
    }

    public void setNextUpdate(long nextUpdate) {
        this.nextUpdate = nextUpdate;
    }
}
