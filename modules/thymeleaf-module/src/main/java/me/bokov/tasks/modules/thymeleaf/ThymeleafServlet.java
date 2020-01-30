package me.bokov.tasks.modules.thymeleaf;

import hu.inbuss.thymeleaf.cdi.CDIWebContextFactory;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

@WebServlet (
        name = "ThymeleafServlet",
        urlPatterns = "/th/*",
        loadOnStartup = 1
)
public class ThymeleafServlet extends HttpServlet {

    @Inject
    private ThymeleafService thymeleafService;

    @Inject
    private CDIWebContextFactory cdiWebContextFactory;

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String uri = req.getRequestURI ()
                .substring (req.getContextPath ().length ())
                .replaceFirst ("/th", "");

        if (uri.startsWith ("/css/") || uri.startsWith ("/js/") || uri.startsWith ("/assets/")) {

            // Serve static resource
            URL resourceURL = req.getServletContext ()
                    .getResource (uri);

            if (resourceURL == null) {
                resp.sendError (404);
                return;
            }

            try (
                    ReadableByteChannel input = Channels.newChannel (resourceURL.openStream ());
                    WritableByteChannel output = Channels.newChannel (resp.getOutputStream ())
            ) {
                ByteBuffer buffer = ByteBuffer.allocateDirect (10240);
                long size = 0;

                while (input.read (buffer) != -1) {
                    buffer.flip ();
                    size += output.write (buffer);
                    buffer.clear ();
                }

                resp.setContentLengthLong (size);

            }

        } else {

            IWebContext context = cdiWebContextFactory.create (req, resp);
            thymeleafService.getTemplateEngine ()
                    .process (uri, context, resp.getWriter ());

        }

    }

}
