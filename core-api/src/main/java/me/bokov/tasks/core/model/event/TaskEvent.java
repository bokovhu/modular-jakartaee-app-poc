package me.bokov.tasks.core.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.bokov.tasks.core.model.domain.Task;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskEvent implements Serializable {

    public enum Action {
        CREATED,
        UPDATED,
        DELETED
    }

    private Action action;
    private String taskId;
    private Task task;
    private LocalDateTime eventTimestamp;

}
