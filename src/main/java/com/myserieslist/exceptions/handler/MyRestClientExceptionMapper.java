package com.myserieslist.exceptions.handler;

import com.myserieslist.exceptions.MySeriesListException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class MyRestClientExceptionMapper implements ResponseExceptionMapper<RuntimeException> {


    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatus() == 500) {
            return new MySeriesListException("The remote service responded with HTTP 500", response.getStatus());
        }

        if (response.getStatus() == 401) {
            return new MySeriesListException("Access denied: You are not authorized to access this resource.", response.getStatus());
        }
        return null;
    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return ResponseExceptionMapper.super.handles(status, headers);
    }

    @Override
    public int getPriority() {
        return ResponseExceptionMapper.super.getPriority();
    }
}
