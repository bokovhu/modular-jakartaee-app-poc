package me.bokov.tasks.modules.thymeleaf;

import me.bokov.tasks.core.module.ModuleRegistry;
import me.bokov.tasks.core.module.ViewExtensionRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class ThymeleafModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    private ThymeleafModule thymeleafModule;

    @PostConstruct
    public void onInit () {

        thymeleafModule = new ThymeleafModule ();
        moduleRegistry.register (thymeleafModule);

    }

}
