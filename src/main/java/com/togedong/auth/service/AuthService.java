package com.togedong.auth.service;

import com.togedong.auth.dto.SignInRequest;
import com.togedong.auth.dto.SignUpRequest;
import com.togedong.auth.dto.TokenResponse;
import com.togedong.auth.dto.UserResponse;

public interface AuthService {

    UserResponse register(final SignUpRequest request);

    TokenResponse login(final SignInRequest request);
}
