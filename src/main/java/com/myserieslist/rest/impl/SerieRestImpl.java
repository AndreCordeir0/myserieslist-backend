package com.myserieslist.rest.impl;


import com.myserieslist.dto.AuthRequest;
import com.myserieslist.dto.SerieRecord;
import com.myserieslist.dto.UserCreateRequest;
import com.myserieslist.enums.RolesEnum;
import com.myserieslist.services.AuthService;
import com.myserieslist.services.SerieService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/serie")
public class SerieRestImpl {

    @Inject
    SerieService serieService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response createSerie(SerieRecord serieRecord) {
        return Response.ok(serieService.save(serieRecord)).build();
    }

}
