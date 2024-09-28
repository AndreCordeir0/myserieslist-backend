package com.myserieslist.rest.client;


import com.myserieslist.dto.AuthResponse;
import com.myserieslist.exceptions.handler.MyRestClientExceptionMapper;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "auth-api")
@RegisterProvider(MyRestClientExceptionMapper.class)
public interface AuthClient {

    @POST
    @Path("/realms/myanimelistrealm/protocol/openid-connect/token")
    public AuthResponse getToken(
            @FormParam("client_id") String clientID,
            @FormParam("client_secret") String clientSecret,
            @FormParam("grant_type") String grantType,
            @FormParam("username") String u,
            @FormParam("password") String p
    );

    @POST
    @Path("/realms/master/protocol/openid-connect/token")
    public AuthResponse getAdminToken(
            @FormParam("client_id") String clientID,
            @FormParam("grant_type") String grantType,
            @FormParam("username") String u,
            @FormParam("password") String p
    );

}
