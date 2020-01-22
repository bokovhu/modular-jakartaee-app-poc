package me.bokov.tasks.modules.languagechooser;

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
public class LanguageChooserModuleInitializer {

    @EJB
    private ModuleRegistry moduleRegistry;

    @Inject
    private ViewExtensionRegistry viewExtensionRegistry;

    private LanguageChooserModule languageChooserModule;

    @PostConstruct
    public void onInit () {

        languageChooserModule = new LanguageChooserModule ();
        moduleRegistry.register (languageChooserModule);

        viewExtensionRegistry.addViewExtension (
                "applicationSidebar",
                new ViewExtension (
                        "/parts/modules/language-chooser/languageChooser.xhtml",
                        100
                )
        );

    }

}
