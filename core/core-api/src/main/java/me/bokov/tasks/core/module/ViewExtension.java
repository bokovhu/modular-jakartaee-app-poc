package me.bokov.tasks.core.module;

import java.io.Serializable;

public class ViewExtension implements Serializable {

    private final String resource;
    private final int order;

    public ViewExtension (String resource) {
        this.resource = resource;
        this.order = 0;
    }

    public ViewExtension (String resource, int order) {
        this.resource = resource;
        this.order = order;
    }

    public String getResource () {
        return resource;
    }

    public int getOrder () {
        return order;
    }

}
