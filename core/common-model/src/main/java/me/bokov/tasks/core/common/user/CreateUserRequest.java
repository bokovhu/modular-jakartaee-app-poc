package me.bokov.tasks.core.common.user;

import lombok.Data;
import me.bokov.tasks.core.common.domain.UserVO;

import java.io.Serializable;

@Data
public class CreateUserRequest implements Serializable {

    private UserVO user;
    private String password;

}
