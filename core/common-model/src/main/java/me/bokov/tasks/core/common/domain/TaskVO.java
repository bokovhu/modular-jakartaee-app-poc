package me.bokov.tasks.core.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskVO implements Serializable {

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE
    }

    private Long id;
    private String summary;
    private String description;
    private Status status;
    private Long assigneeId;

}
