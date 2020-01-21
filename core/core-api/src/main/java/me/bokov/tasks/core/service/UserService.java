package me.bokov.tasks.core.service;

import me.bokov.tasks.core.common.domain.UserVO;
import me.bokov.tasks.core.common.user.CreateUserRequest;
import me.bokov.tasks.core.common.user.LoginRequest;
import me.bokov.tasks.core.common.user.LoginResponse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {

    LoginResponse login (LoginRequest request);
    UserVO createUser (CreateUserRequest request);
    void updateUser (UserVO user);
    void removeUser (UserVO user);
    List <UserVO> getAllUsers ();

}
