package me.bokov.tasks.modules.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.resource.spi.AuthenticationMechanism;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
@AutoApplySession
@LoginToContinue (
        loginPage = "/public/modules/login/login.xhtml",
        errorPage = "/public/modules/login/login.xhtml?error=true",
        useForwardToLogin = false
)
public class TasksAppHttpAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Override
    public AuthenticationStatus validateRequest (
            HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext
    ) throws AuthenticationException {

        IdentityStoreHandler identityStoreHandler = CDI.current ().select (IdentityStoreHandler.class).get ();

        if (containsValidCredentials (httpMessageContext)) {
            return httpMessageContext.notifyContainerAboutLogin (
                    identityStoreHandler.validate (
                            httpMessageContext.getAuthParameters ().getCredential ()
                    )
            );
        } else if (isFormLogin (request)) {
            return httpMessageContext.notifyContainerAboutLogin (
                    identityStoreHandler.validate (
                            new UsernamePasswordCredential (
                                    request.getParameter ("j_username"),
                                    request.getParameter ("j_password")
                            )
                    )
            );
        } else if (isJWTBasedAuthentication (request)) {
            return httpMessageContext.notifyContainerAboutLogin (
                    identityStoreHandler.validate (
                            new JWTCredential (
                                    getJWTFromRequest (request)
                            )
                    )
            );
        }

        return httpMessageContext.doNothing ();
    }

    private boolean isFormLogin (final HttpServletRequest req) {
        return "POST".equals (req.getMethod ())
                && req.getRequestURI ().endsWith ("/j_security_check")
                && req.getParameter ("j_username") != null
                && req.getParameter ("j_password") != null;
    }

    private boolean isJWTBasedAuthentication (final HttpServletRequest req) {
        return (
                req.getHeader ("authorization") != null
                && req.getHeader ("authorization").startsWith ("Bearer ")
                )
                || (req.getHeader ("x-jwt") != null);
    }

    private boolean containsValidCredentials (HttpMessageContext ctx) {
        return ctx.getAuthParameters ().getCredential () instanceof UsernamePasswordCredential
                || ctx.getAuthParameters ().getCredential () instanceof JWTCredential;
    }

    private String getJWTFromRequest (final HttpServletRequest req) {
        return req.getHeader ("authorization") != null
                ? (req.getHeader ("authorization").substring ("Bearer ".length ()))
                : (req.getHeader ("x-jwt"));
    }

}
