package me.bokov.tasks.core.common.event;

import lombok.Data;
import me.bokov.tasks.core.common.domain.UserVO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserEvent implements Serializable {

    public enum Action {
        LOGIN,
        LOGOUT,
        CREATED,
        UPDATED,
        DELETED
    }

    private Action action;
    private LocalDateTime eventTimestamp;
    private Long userId;
    private UserVO user;

}
