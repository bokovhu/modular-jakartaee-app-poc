package me.bokov.tasks.modules.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import me.bokov.tasks.core.module.ModuleRegistry;
import me.bokov.tasks.dal.dao.UserDao;
import me.bokov.tasks.dal.entity.UserEntity;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Arrays;
import java.util.HashSet;

@Singleton
@Startup
@Slf4j
@DeclareRoles ({"USER", "ADMIN"})
public class UserModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @EJB
    private UserDao userDao;

    private UserModule userModule;

    @PostConstruct
    public void onInit () {

        this.userModule = new UserModule ();
        moduleRegistry.register (this.userModule);

        createAdminUser ();

    }

    private void createAdminUser () {

        UserEntity admin = userDao.findByLoginName ("admin");
        if (admin == null) {

            admin = new UserEntity ();
            admin.setDisplayName ("System Administrator");
            admin.setLoginName ("admin");
            admin.setPasswordHash (
                    BCrypt.withDefaults ()
                    .hashToString (
                            12,
                            "admin".toCharArray ()
                    )
            );
            admin.setEmail ("admin@company.localhost");
            admin.setRoles (
                    new HashSet<> (
                            Arrays.asList ("USER", "ADMIN")
                    )
            );

            userDao.create (admin);

        }

    }

}
