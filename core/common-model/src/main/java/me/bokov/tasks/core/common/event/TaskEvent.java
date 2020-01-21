package me.bokov.tasks.core.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.bokov.tasks.core.common.domain.TaskVO;

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
    private Long taskId;
    private TaskVO task;
    private LocalDateTime eventTimestamp;

}
