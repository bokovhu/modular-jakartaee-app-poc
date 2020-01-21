package me.bokov.tasks.core.model;

import me.bokov.tasks.core.common.domain.UserVO;

import javax.security.enterprise.CallerPrincipal;

public class UserPrincipal extends CallerPrincipal {

    private final UserVO user;

    public UserPrincipal (UserVO user) {
        super (user.getLoginName ());
        this.user = user;
    }

    public UserVO getUser () {
        return user;
    }

}
