package me.bokov.tasks.modules.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContext;

@ApplicationScoped
@Slf4j
public class ThymeleafService {

    private TemplateEngine templateEngine;
    private ServletContextTemplateResolver templateResolver;

    private void configureTemplateResolver () {

        templateResolver.setTemplateMode (TemplateMode.HTML);
        templateResolver.setPrefix ("/th");
        templateResolver.setSuffix (".html");
        templateResolver.setCacheTTLMs (1000L * 60L * 60L);
        templateResolver.setCacheable (true);

    }

    private void configureTemplateEngine () {

        templateEngine.setTemplateResolver (templateResolver);

    }

    public void initialize (final ServletContext servletContext) {

        log.info ("Initializing Thymeleaf engine");

        this.templateResolver = new ServletContextTemplateResolver (servletContext);
        configureTemplateResolver ();

        this.templateEngine = new TemplateEngine ();
        configureTemplateEngine ();

        log.info ("Thymeleaf engine initialized");

    }

    public TemplateEngine getTemplateEngine () {
        return templateEngine;
    }

    public ServletContextTemplateResolver getTemplateResolver () {
        return templateResolver;
    }

}
