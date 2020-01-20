package me.bokov.tasks.modules.config;

import me.bokov.tasks.core.module.ModuleRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@Startup
public class ConfigurationModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    private ConfigurationModule configurationModule;

    @PostConstruct
    public void onInit () {

        configurationModule = new ConfigurationModule ();
        moduleRegistry.register (configurationModule);

    }

}
