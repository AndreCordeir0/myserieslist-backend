package com.myserieslist.dto;

import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull(message = "Username cannot be null.") String username,
        @NotNull(message = "Password cannot be null.") String password,
        @NotNull(message = "Email cannot be null.") String email,
        @NotNull(message = "Age cannot be null.") Integer age
) {
}
