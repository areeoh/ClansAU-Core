package com.areeoh.framework;


public abstract class Module<T extends Manager> extends Frame {

    private T manager;

    public Module(T manager, String moduleName) {
        super(manager.getPlugin(), moduleName);

        this.manager = manager;
    }

    public T getManager() {
        return this.manager;
    }

}