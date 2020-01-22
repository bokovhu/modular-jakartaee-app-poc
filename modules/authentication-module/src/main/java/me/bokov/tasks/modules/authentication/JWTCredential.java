package me.bokov.tasks.modules.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.inject.spi.CDI;
import javax.security.enterprise.credential.Credential;
import java.util.Date;

public class JWTCredential implements Credential {

    private final String token;

    private boolean verified;
    private String userLoginName;
    private Date expiresAt;

    public JWTCredential (String token) {
        this.token = token;

        DecodedJWT jwt = CDI.current ().select (JWTHelper.class).get ().verify (token);
        if (jwt == null) {
            this.verified = false;
        } else {
            this.verified = true;
            this.userLoginName = jwt.getClaim ("uid").asString ();
            this.expiresAt = jwt.getExpiresAt ();
        }

    }

    public String getToken () {
        return token;
    }

    public boolean isVerified () {
        return verified;
    }

    public String getUserLoginName () {
        return userLoginName;
    }

    public Date getExpiresAt () {
        return expiresAt;
    }

    @Override
    public boolean isValid () {
        return verified;
    }

}
