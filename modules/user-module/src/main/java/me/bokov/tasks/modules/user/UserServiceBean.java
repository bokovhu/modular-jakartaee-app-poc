package me.bokov.tasks.modules.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.bokov.tasks.core.common.domain.UserVO;
import me.bokov.tasks.core.common.user.CreateUserRequest;
import me.bokov.tasks.core.common.user.LoginRequest;
import me.bokov.tasks.core.common.user.LoginResponse;
import me.bokov.tasks.core.service.UserService;
import me.bokov.tasks.dal.dao.UserDao;
import me.bokov.tasks.dal.entity.UserEntity;
import me.bokov.tasks.modules.user.util.UserVOConverter;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Local (UserService.class)
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class UserServiceBean implements UserService {

    @EJB
    private UserDao userDao;

    @Inject
    private UserVOConverter userVOConverter;

    @Override
    public LoginResponse login (LoginRequest request) {

        LoginResponse response = new LoginResponse ();

        UserEntity userEntity = userDao.findByLoginName (request.getLoginName ());

        if (userEntity == null) {
            response.setSuccessful (false);
            return response;
        }

        BCrypt.Result passwordVerificationResult = BCrypt.verifyer ()
                .verify (
                        request.getPassword (),
                        userEntity.getPasswordHash ()
                );

        if (!passwordVerificationResult.verified) {
            response.setSuccessful (false);
            return response;
        }

        response.setSuccessful (true);
        response.setUser (userVOConverter.userEntityToVO (userEntity));

        return response;

    }

    @Override
    public UserVO createUser (CreateUserRequest request) {

        UserEntity userEntity = new UserEntity ();

        userEntity.setDisplayName (request.getUser ().getDisplayName ());
        userEntity.setLoginName (request.getUser ().getLoginName ());
        userEntity.setEmail (request.getUser ().getEmail ());
        userEntity.setPasswordHash (
                BCrypt.withDefaults ()
                        .hashToString (12, request.getPassword ().toCharArray ())
        );
        userEntity.setRoles (new HashSet<> (request.getUser ().getRoles ()));

        userDao.create (userEntity);

        return userVOConverter.userEntityToVO (userEntity);

    }

    @Override
    public void updateUser (UserVO user) {

        UserEntity userEntity = userDao.findById (user.getId ());

        userEntity.setDisplayName (user.getDisplayName ());
        userEntity.setEmail (user.getEmail ());
        userEntity.setRoles (new HashSet<> (user.getRoles ()));

    }

    @Override
    public void removeUser (UserVO user) {

        UserEntity userEntity = userDao.findById (user.getId ());
        if (userEntity != null) {
            userDao.delete (userEntity);
        }

    }

    @Override
    public List<UserVO> getAllUsers () {

        return userDao.findAll ()
                .stream ()
                .map (userVOConverter::userEntityToVO)
                .collect (Collectors.toList ());

    }

}
