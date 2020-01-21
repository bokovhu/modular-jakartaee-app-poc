package me.bokov.tasks.web.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;

@ApplicationScoped
@FormAuthenticationMechanismDefinition (
        loginToContinue = @LoginToContinue (
                loginPage = "/public/modules/login/login.xhtml",
                errorPage = "/public/modules/login/login.xhtml?error=true",
                useForwardToLogin = false
        )
)
public class SecurityConfigurerBean {

}
