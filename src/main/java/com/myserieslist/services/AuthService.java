package com.myserieslist.services;

import com.myserieslist.dto.*;

public interface AuthService {

    AuthResponse createUser(UserCreateRequest userRequest);

    AuthResponse getToken(AuthRequest authRequest);

    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
