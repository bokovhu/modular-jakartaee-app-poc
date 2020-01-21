package me.bokov.tasks.modules.user.util;

import me.bokov.tasks.core.common.domain.UserVO;
import me.bokov.tasks.dal.entity.UserEntity;

import javax.enterprise.context.RequestScoped;
import java.util.HashSet;

@RequestScoped
public class UserVOConverter {

    public UserVO userEntityToVO (UserEntity entity) {

        UserVO vo = new UserVO ();

        vo.setId (entity.getId ());
        vo.setDisplayName (entity.getDisplayName ());
        vo.setLoginName (entity.getLoginName ());
        vo.setEmail (entity.getEmail ());
        vo.setRoles (new HashSet<> ());
        vo.getRoles ().addAll (entity.getRoles ());

        return vo;

    }

}
