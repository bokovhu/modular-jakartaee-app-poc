package me.bokov.tasks.modules.login;

import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class LogoutBean {

    public void logout () {

        Faces.invalidateSession ();
        Faces.redirect (Faces.getRequestContextPath ());

    }

}
