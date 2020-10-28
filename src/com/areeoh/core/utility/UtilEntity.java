package com.areeoh.core.utility;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class UtilEntity {

    public static <T extends Entity> List<T> getNearbyEntities(Entity ent, Class<T> entityType, float range, float y) {
        List<T> entities = new ArrayList<>();

        for (Entity entity : ent.getNearbyEntities(range, y, range)) {
            if (entityType.isAssignableFrom(entity.getClass())) {
                entities.add(entityType.cast(entity));
            }
        }
        return entities;
    }

}
