package com.myserieslist.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_entity", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dykn684sl8up1crfei6eckhd7", columnNames = {"realm_id", "email_constraint"}),
                @UniqueConstraint(name = "uk_ru8tt6t700s9v50bu18ws5ha6", columnNames = {"realm_id", "username"})
        },
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_service_account", columnList = "realm_id, service_account_client_link")
        }
)
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "email_constraint", length = 255)
    private String emailConstraint;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "federation_link", length = 255)
    private String federationLink;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "realm_id", length = 255)
    private String realmId;

    @Column(name = "username", length = 255)
    private String username;

    @Column(name = "created_timestamp")
    private Long createdTimestamp;

    @Column(name = "service_account_client_link", length = 255)
    private String serviceAccountClientLink;

    @Column(name = "not_before", nullable = false)
    private int notBefore = 0;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConstraint() {
        return emailConstraint;
    }

    public void setEmailConstraint(String emailConstraint) {
        this.emailConstraint = emailConstraint;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFederationLink() {
        return federationLink;
    }

    public void setFederationLink(String federationLink) {
        this.federationLink = federationLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getServiceAccountClientLink() {
        return serviceAccountClientLink;
    }

    public void setServiceAccountClientLink(String serviceAccountClientLink) {
        this.serviceAccountClientLink = serviceAccountClientLink;
    }

    public int getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(int notBefore) {
        this.notBefore = notBefore;
    }
}
