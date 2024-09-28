package com.myserieslist.services;

import com.myserieslist.dto.AuthResponse;
import org.keycloak.admin.client.Keycloak;

public interface MySeriesListBaseService {


    AuthResponse getAdminAuth();

    Keycloak getKeyCloakAdminInstance();
}
