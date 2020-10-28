package com.areeoh.core.framework.updater;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {

    int ticks() default 20;
    boolean async() default false;

//    https://www.youtube.com/watch?v=J2GohD6r8Co
//    Good resource to learn more about this

}
