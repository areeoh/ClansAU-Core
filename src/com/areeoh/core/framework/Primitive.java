package com.areeoh.core.framework;

public class Primitive<T> {

    private T object;
    private final T defaultValue;

    public Primitive(T object, T defaultValue) {
        this.object = object;
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue() { return defaultValue; }

    public Object getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void resetToDefault() {
        setObject(getDefaultValue());
    }
}
