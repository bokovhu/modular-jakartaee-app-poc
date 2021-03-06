package me.bokov.tasks.modules.task;

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
public class TaskModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    private TaskModule taskModule;

    @PostConstruct
    public void onInit () {

        taskModule = new TaskModule ();
        moduleRegistry.register (taskModule);

        viewExtensionRegistry.addViewExtension (
                "mainMenu.menuItems",
                new ViewExtension (
                        "/parts/modules/task/taskMenuItem.xhtml",
                        20
                )
        );

    }

}
