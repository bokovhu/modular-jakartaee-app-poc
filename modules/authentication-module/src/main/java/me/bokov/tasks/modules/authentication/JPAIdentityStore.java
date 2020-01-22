package me.bokov.tasks.modules.authentication;

import me.bokov.tasks.core.common.user.APILoginRequest;
import me.bokov.tasks.core.common.user.APILoginResponse;
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

        } else if (credential instanceof JWTCredential) {

            JWTCredential jwt = (JWTCredential) credential;

            APILoginRequest loginRequest = new APILoginRequest ();

            loginRequest.setUserLoginName (jwt.getUserLoginName ());

            APILoginResponse response = userService.loginForAPI (loginRequest);

            if (response.isSuccessful ()) {
                return new CredentialValidationResult (
                        new UserPrincipal (response.getUser ()),
                        response.getUser ().getRoles ()
                );
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }

        }

        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

}
