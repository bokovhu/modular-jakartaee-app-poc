package me.bokov.tasks.core.common.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    private String loginName;
    private char[] password;

}
