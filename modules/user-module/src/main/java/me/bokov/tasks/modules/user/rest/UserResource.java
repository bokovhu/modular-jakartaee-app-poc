package me.bokov.tasks.modules.user.rest;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path ("user")
@RequestScoped
public class UserResource {

    @Context
    private SecurityContext securityContext;

    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response get () throws Exception {

        return Response.ok(
                securityContext.getUserPrincipal ().getName ()
        ).build();

    }

}
