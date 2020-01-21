package me.bokov.tasks.core.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserVO implements Serializable {

    private Long id;
    private String displayName;
    private String loginName;
    private String email;
    private Set <String> roles;

}
