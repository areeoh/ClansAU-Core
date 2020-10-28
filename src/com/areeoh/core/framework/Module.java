package com.areeoh.core.framework;


import java.util.HashMap;
import java.util.Map;

public abstract class Module<T extends Manager> extends Frame {

    private T manager;
    private Map<String, Primitive> primitives;

    public Module(T manager, String moduleName) {
        super(manager.getPlugin(), moduleName);

        this.manager = manager;
        this.primitives = new HashMap<>();
    }

    public Map<String, Primitive> getPrimitives() {
        return primitives;
    }

    public void addPrimitive(String primitiveName, Primitive primitive) {
        getPrimitives().put(primitiveName, primitive);
    }

    public Map.Entry<String, Primitive> getPrimitive(String name) {
        for (Map.Entry<String, Primitive> entry : getPrimitives().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry;
            }
        }
        return null;
    }

    public Map.Entry<String, Primitive> getPrimitive(Class<?> clazz, String name) {
        for (Map.Entry<String, Primitive> entry : getPrimitives().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                if (clazz.isInstance(entry.getValue().getObject())) {
                    return entry;
                }
            }
        }
        return null;
    }

    public <E> E getPrimitiveCasted(Class<? extends E> clazz, String name) {
        for (Map.Entry<String, Primitive> entry : getPrimitives().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return clazz.cast(entry.getValue().getObject());
            }
        }
        return null;
    }

    public T getManager() {
        return this.manager;
    }
}