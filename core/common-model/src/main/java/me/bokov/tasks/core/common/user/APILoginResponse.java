package me.bokov.tasks.core.common.user;

import lombok.Data;
import me.bokov.tasks.core.common.domain.UserVO;

import java.io.Serializable;

@Data
public class APILoginResponse implements Serializable {

    private boolean successful;
    private UserVO user;

}
