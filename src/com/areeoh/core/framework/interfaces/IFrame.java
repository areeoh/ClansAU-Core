package com.areeoh.core.framework.interfaces;


import com.areeoh.core.framework.Plugin;

public interface IFrame {

    String getName();

    void initialize(Plugin javaPlugin);

    void shutdown();
}
