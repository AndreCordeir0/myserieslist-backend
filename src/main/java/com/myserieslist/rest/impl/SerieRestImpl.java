package com.myserieslist.rest.impl;


import com.myserieslist.dto.Pagination;
import com.myserieslist.dto.SerieRecord;
import com.myserieslist.services.SerieService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Path("/serie")
public class SerieRestImpl {

    @Inject
    SerieService serieService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response createSerie(SerieRecord serieRecord) throws IOException {
        return Response.ok(serieService.save(serieRecord)).build();
    }

    @POST
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response paginated(Pagination<SerieRecord> serieRecord) throws IOException {
        return Response.ok(serieService.paginated(serieRecord)).build();
    }

}
