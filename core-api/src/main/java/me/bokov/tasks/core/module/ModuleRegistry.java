package me.bokov.tasks.core.module;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@LocalBean
@Singleton
@ConcurrencyManagement (ConcurrencyManagementType.CONTAINER)
@Slf4j
public class ModuleRegistry {

    private final List <Module> registeredModules = new ArrayList<> ();

    @Lock (LockType.WRITE)
    public void register (Module module) {

        registeredModules.add (module);
        log.info ("Registered module {}", module.getName ());

    }

    @Lock (LockType.READ)
    public List <Module> getModules () {
        return Collections.unmodifiableList (registeredModules);
    }

}
