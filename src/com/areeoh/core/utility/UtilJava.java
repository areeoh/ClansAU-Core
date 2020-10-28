package com.areeoh.core.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UtilJava {

    public static <T> T searchList(ArrayList<T> list, Predicate<? super T> predicate) {
        list.removeIf(t -> !predicate.test(t));
        return list.size() == 1 ? list.get(0) : null;
    }

    public static Class<?> getClass(String className, boolean... inner) {
        Class<?> type = null;
        boolean in = (inner.length > 0) && inner[0];
        try {
            if (in) {

                int last = className.lastIndexOf(".");
                className = className.substring(0, last) + "$" + className.substring(last + 1);
            }
            type = Class.forName(className);
        } catch (ClassNotFoundException ignored) {}
        return type;
    }
}