package me.bokov.tasks.web.security;

import me.bokov.tasks.core.common.user.LoginRequest;
import me.bokov.tasks.core.common.user.LoginResponse;
import me.bokov.tasks.core.model.UserPrincipal;
import me.bokov.tasks.core.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class JPAIdentityStore implements IdentityStore {

    @EJB
    private UserService userService;

    @Override
    public CredentialValidationResult validate (Credential credential) {

        if (!credential.isValid () || credential.isCleared ()) return CredentialValidationResult.NOT_VALIDATED_RESULT;
        if (credential instanceof UsernamePasswordCredential) {

            UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;

            LoginRequest loginRequest = new LoginRequest ();

            loginRequest.setLoginName (usernamePassword.getCaller ());
            loginRequest.setPassword (usernamePassword.getPassword ().getValue ());

            LoginResponse loginResponse = userService.login (loginRequest);

            if (loginResponse.isSuccessful ()) {
                return new CredentialValidationResult (
                        new UserPrincipal (loginResponse.getUser ()),
                        loginResponse.getUser ().getRoles ()
                );
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }

        }

        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

}
