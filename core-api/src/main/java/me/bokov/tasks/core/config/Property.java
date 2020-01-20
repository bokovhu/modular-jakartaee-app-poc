package me.bokov.tasks.core.config;

import java.io.Serializable;

public class Property implements Serializable {

    private final String key;
    private final String value;

    public Property (String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue () {
        return key;
    }

    public String getKey () {
        return key;
    }

    public Integer getIntValue () {
        return Integer.parseInt (value);
    }

    public Boolean getBooleanValue () {
        return Boolean.parseBoolean (value);
    }

}
