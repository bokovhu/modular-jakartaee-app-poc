package me.bokov.tasks.modules.task;

import me.bokov.tasks.core.module.ModuleRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TaskModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    private TaskModule taskModule;

    @PostConstruct
    public void onInit () {

        taskModule = new TaskModule ();
        moduleRegistry.register (taskModule);

    }

}
