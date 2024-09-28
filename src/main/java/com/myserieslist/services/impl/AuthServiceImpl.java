package com.myserieslist.services.impl;

import com.myserieslist.dto.*;
import com.myserieslist.enums.RolesEnum;
import com.myserieslist.exceptions.MySeriesListException;
import com.myserieslist.services.AuthService;
import com.myserieslist.workaround.CustomUserRepresentation;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.List;

@RequestScoped
public class AuthServiceImpl extends MySeriesListBaseServiceImpl implements AuthService  {


    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientID;

    @ConfigProperty(name = "quarkus.oidc-client.grant.type")
    String grantType;

    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String secret;

    @ConfigProperty(name = "my.series.list.oidc.user.realm")
    String userRealm;

    @ConfigProperty(name = "my.series.list.oidc.refresh.token.grant.type")
    String refreshGrantType;

    @Override
    public AuthResponse createUser(UserCreateRequest userRequest) {
        Keycloak keycloak = getKeyCloakAdminInstance();
        CustomUserRepresentation keycloakUser = getUserRepresentation(userRequest);

        RealmResource realmResource = keycloak.realm(userRealm);
        UsersResource usersResource = realmResource.users();

        Response result = getResponseCreateUser(usersResource, keycloakUser, keycloak);
        handleResult(result);
        AuthRequest authRequest = new AuthRequest(
                userRequest.username(),
                userRequest.password()
        );

        return getToken(authRequest);
    }

    private static void handleResult(Response result) {
        if (result ==null || result.getStatus() != 201) {
            if (result != null) {
                KeycloakResponse response =  result.readEntity(KeycloakResponse.class);
                result.close();
                throw new MySeriesListException((response.errorMessage()), result.getStatus());
            }
            throw new MySeriesListException("Unexpected error occurred during create", 500);
        }
    }

    private Response getResponseCreateUser(
            UsersResource usersResource,
            CustomUserRepresentation keycloakUser,
            Keycloak keycloak
    ) {
        Response result = null;
        try {
            result = usersResource.create(keycloakUser);
        } catch(Exception e) {
            Log.error(e);
            result.close();
            throw new MySeriesListException("Unexpected error occurred during create", 500);
        } finally {
            keycloak.close();
        }
        return result;
    }

    private static CustomUserRepresentation getUserRepresentation(UserCreateRequest userRequest) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userRequest.password());
        credential.setTemporary(false);
        CustomUserRepresentation keycloakUser = new CustomUserRepresentation();
        keycloakUser.setUsername(userRequest.username());
        keycloakUser.setEmail(userRequest.email());
        keycloakUser.setCredentials(List.of(credential));
        keycloakUser.setEnabled(true);
        keycloakUser.setRealmRoles(List.of(RolesEnum.USER.getKey()));
        return keycloakUser;
    }

    @Override
    public AuthResponse getToken(AuthRequest authRequest) {
        return authClient.getToken(
                clientID,
                secret,
                grantType,
                authRequest.username(),
                authRequest.password()
        );
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }
}
