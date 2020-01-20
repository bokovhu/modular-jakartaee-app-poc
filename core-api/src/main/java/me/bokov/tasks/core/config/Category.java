package me.bokov.tasks.core.config;

import java.io.Serializable;
import java.util.Map;

public class Category implements Serializable {

    private final String name;
    private final Map <String, Property> properties;

    public Category (
            String name,
            Map<String, Property> properties
    ) {
        this.name = name;
        this.properties = properties;
    }

    public String getName () {
        return name;
    }

    public Property getProperty (String key) {
        return properties.get (key);
    }

}
