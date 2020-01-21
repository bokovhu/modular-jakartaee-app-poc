package me.bokov.tasks.modules.login;

import me.bokov.tasks.core.module.ModuleRegistry;
import me.bokov.tasks.core.module.ViewExtension;
import me.bokov.tasks.core.module.ViewExtensionRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class LoginModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    private LoginModule loginModule;

    @PostConstruct
    public void onInit () {

        loginModule = new LoginModule ();
        moduleRegistry.register (loginModule);

        viewExtensionRegistry.addViewExtension (
                "mainMenu.menuItems",
                new ViewExtension ("/parts/modules/login/logoutMenuItem.xhtml", 100)
        );

    }

}
