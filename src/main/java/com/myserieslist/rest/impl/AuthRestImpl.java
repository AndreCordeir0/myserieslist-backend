package com.myserieslist.rest.impl;


import com.myserieslist.dto.AuthRequest;
import com.myserieslist.dto.UserCreateRequest;
import com.myserieslist.services.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthRestImpl {

    @Inject
    AuthService authService;



    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response createUser(UserCreateRequest userRequest) {
        return Response.ok(authService.createUser(userRequest)).build();
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getToken(AuthRequest authRequest) {
        return Response.ok(authService.getToken(authRequest)).build();
    }
}
