package me.bokov.tasks.modules.tasklogger;

import me.bokov.tasks.core.module.ModuleRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TaskLoggerModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    private TaskLoggerModule taskLoggerModule;

    @PostConstruct
    public void onInit () {

        taskLoggerModule = new TaskLoggerModule ();
        moduleRegistry.register (taskLoggerModule);

    }

}
