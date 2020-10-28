package com.areeoh.core.framework.updater;

import com.areeoh.core.ClansAUCore;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.framework.Plugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages class update tasks
 * Expand on this system later for async + callbacks
 */
public class UpdateManager extends Manager<Module> implements Runnable {

    private int taskID;
    private long lastTick;
    private Set<UpdateTask> updateTasks = new HashSet<>();

    public UpdateManager(ClansAUCore plugin) {
        super(plugin, "UpdateManager");
    }

    public void addTask(UpdateTask task) {
        this.updateTasks.add(task);
    }

    public Set<UpdateTask> getTasks() {
        return this.updateTasks;
    }

    @Override
    public void run() {
        lastTick = System.currentTimeMillis();
        for (UpdateTask task : updateTasks) {
            task.run(lastTick);
        }
    }

    @Override
    public void initialize(Plugin javaPlugin) {
        taskID = getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), this, 0L, 0L).getTaskId();
    }

    @Override
    public void shutdown() {
        getPlugin().getServer().getScheduler().cancelTask(taskID);
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void registerModules() {
    }
}
