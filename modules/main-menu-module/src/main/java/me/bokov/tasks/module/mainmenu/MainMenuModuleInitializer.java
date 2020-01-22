package me.bokov.tasks.module.mainmenu;

import me.bokov.tasks.core.module.ModuleRegistry;
import me.bokov.tasks.core.module.ViewExtension;
import me.bokov.tasks.core.module.ViewExtensionRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class MainMenuModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    private MainMenuModule mainMenuModule;

    @PostConstruct
    public void onInit () {

        mainMenuModule = new MainMenuModule ();
        moduleRegistry.register (mainMenuModule);

        viewExtensionRegistry.addViewExtension (
                "applicationSidebar",
                new ViewExtension ("/parts/modules/main-menu/mainMenu.xhtml", 0)
        );

    }

}
