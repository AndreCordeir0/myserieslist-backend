package com.myserieslist.exceptions.handler;

import com.myserieslist.exceptions.ErrorMessage;
import com.myserieslist.exceptions.MySeriesListException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MySerisListExceptionMapper implements ExceptionMapper<MySeriesListException> {

    @Override
    public Response toResponse(MySeriesListException e) {
        return Response.status(e.getStatus()).entity(new ErrorMessage(e.getMessage())).build();
    }
}
