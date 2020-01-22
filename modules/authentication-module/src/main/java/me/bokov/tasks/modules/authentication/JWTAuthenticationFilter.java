package me.bokov.tasks.modules.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter (
        urlPatterns = "/rest/*"
)
public class JWTAuthenticationFilter extends HttpFilter {

    @Inject
    private SecurityContext securityContext;

    private String extractJWTFromRequest (HttpServletRequest req) {

        final String authorization = req.getHeader ("authorization");
        if (authorization != null) {
            if (authorization.startsWith ("Bearer ")) return authorization.substring ("Bearer ".length ());
            return null;
        }

        final String xJwt = req.getHeader ("x-jwt");
        if (xJwt != null) {
            return xJwt;
        }

        return null;

    }

    @Override
    protected void doFilter (HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String token = extractJWTFromRequest (req);

        if (token != null) {

            AuthenticationStatus authStatus = securityContext.authenticate (
                    req, res,
                    AuthenticationParameters.withParams ()
                            .newAuthentication (true)
                            .rememberMe (false)
                            .credential (new JWTCredential (token))
            );

            switch (authStatus) {
                case NOT_DONE:
                case SUCCESS:
                case SEND_CONTINUE:
                    chain.doFilter (req, res);
                    break;
                case SEND_FAILURE:
                    res.sendError (401);
                    return;
            }

        } else {
            chain.doFilter (req, res);
        }

    }

}
