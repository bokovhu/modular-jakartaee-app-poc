package me.bokov.tasks.modules.authentication;

import at.favre.lib.bytes.Bytes;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@ApplicationScoped
public class JWTHelper {

    private static final String JWT_ISSUER = "me.bokov.tasks";
    private String jwtSecret;

    @PostConstruct
    public void onInit () {

        String secretDirPath = System.getProperty ("jboss.server.data.dir", System.getProperty ("user.home"));
        File secretFile = new File (secretDirPath, ".tasks-jwt-secret");

        if (!secretFile.exists ()) {

            SecureRandom random = new SecureRandom ();
            jwtSecret = Bytes.random (32, random)
                    .encodeBase64 ();
            try (
                    FileWriter fw = new FileWriter (secretFile)
            ) {
                fw.write (jwtSecret);
            } catch (IOException e) {
                e.printStackTrace ();
            }

        } else {

            try (
                    FileReader fr = new FileReader (secretFile);
                    BufferedReader br = new BufferedReader (fr)
            ) {
                jwtSecret = br.readLine ().strip ();
            } catch (IOException e) {
                e.printStackTrace ();
                SecureRandom random = new SecureRandom ();
                jwtSecret = Bytes.random (32, random)
                        .encodeBase64 ();
            }

        }

        String test = JWT.create ()
                .withClaim ("uid", "admin")
                .withExpiresAt (new Date (System.currentTimeMillis () + 1000L * 60L * 60L))
                .withIssuedAt (new Date ())
                .withIssuer (JWT_ISSUER)
                .sign (Algorithm.HMAC256 (jwtSecret));

        log.info ("JWT: {}", test);

    }

    public DecodedJWT verify (String token) {

        try {
            return JWT.require (Algorithm.HMAC256 (jwtSecret))
                    .withIssuer (JWT_ISSUER)
                    .build ()
                    .verify (token);
        } catch (JWTVerificationException jwtEx) {
            return null;
        }

    }

}
