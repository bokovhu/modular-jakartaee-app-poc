package me.bokov.tasks.core.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Task implements Serializable {

    private String id;
    private String summary;
    private String description;

}
