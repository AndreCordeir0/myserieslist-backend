package com.myserieslist.workaround;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.keycloak.representations.idm.UserProfileMetadata;
import org.keycloak.representations.idm.UserRepresentation;

public class CustomUserRepresentation extends UserRepresentation {
    @JsonIgnore
    @Override
    public void setUserProfileMetadata(UserProfileMetadata userProfileMetadata) {
        super.setUserProfileMetadata(userProfileMetadata);
    }

    @JsonIgnore
    @Override
    public UserProfileMetadata getUserProfileMetadata() {
        return super.getUserProfileMetadata();
    }
}