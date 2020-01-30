package me.bokov.tasks.modules.thymeleaf;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThymeleafServletContextListener implements ServletContextListener {

    @Inject
    private ThymeleafService thymeleafService;

    @Override
    public void contextInitialized (ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext ();
        thymeleafService.initialize (servletContext);

    }

}
