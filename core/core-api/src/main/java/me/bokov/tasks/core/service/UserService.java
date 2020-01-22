package me.bokov.tasks.core.service;

import me.bokov.tasks.core.common.domain.UserVO;
import me.bokov.tasks.core.common.user.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {

    LoginResponse login (LoginRequest request);
    UserVO createUser (CreateUserRequest request);
    void updateUser (UserVO user);
    void removeUser (UserVO user);
    List <UserVO> getAllUsers ();
    APILoginResponse loginForAPI (APILoginRequest request);

}
