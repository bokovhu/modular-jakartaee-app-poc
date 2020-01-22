package me.bokov.tasks.core.common.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class APILoginRequest implements Serializable {

    private String userLoginName;

}
