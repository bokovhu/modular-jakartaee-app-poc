package me.bokov.tasks.modules.authentication;

import me.bokov.tasks.core.module.ModuleRegistry;
import me.bokov.tasks.core.module.ViewExtensionRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class AuthenticationModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    @Inject
    private JWTHelper jwtHelper;

    private AuthenticationModule authenticationModule;

    @PostConstruct
    public void onInit () {

        jwtHelper.onInit ();

        authenticationModule = new AuthenticationModule ();
        moduleRegistry.register (authenticationModule);

    }

}
