package com.myserieslist.services.impl;

import com.myserieslist.dto.AuthResponse;
import com.myserieslist.rest.client.AuthClient;
import com.myserieslist.services.MySeriesListBaseService;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@ApplicationScoped
public class MySeriesListBaseServiceImpl implements MySeriesListBaseService {

    @RestClient
    AuthClient authClient;

    @ConfigProperty(name = "my.series.list.oidc.admin.client.id")
    String adminClientId;

    @ConfigProperty(name = "my.series.list.oidc.admin.username")
    String adminUsername;

    @ConfigProperty(name = "my.series.list.oidc.admin.password")
    String adminPassword;

    @ConfigProperty(name = "quarkus.oidc-client.grant.type")
    String grantType;


    @Override
    @CacheResult(cacheName = "admin-auth")
    public AuthResponse getAdminAuth() {
        AuthResponse authResponse = authClient.getAdminToken(
                adminClientId,
                grantType,
                adminUsername,
                adminPassword
        );
        return authResponse;
    }

    public Keycloak getKeyCloakAdminInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8090/")
                .realm("master")
                .grantType("password")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .build();
    }

}
