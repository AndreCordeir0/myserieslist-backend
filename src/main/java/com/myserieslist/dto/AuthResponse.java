package com.myserieslist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(

        @JsonProperty("access_token")
        String acessToken,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("refresh_expires_in")
        Integer refreshExpiresIn,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("session_state")
        String sessionState,

        @JsonProperty("scope")
        String scope
) {
}
