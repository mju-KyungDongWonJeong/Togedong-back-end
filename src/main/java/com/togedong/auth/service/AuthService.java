package com.togedong.auth.service;

import com.togedong.auth.controller.dto.SignInRequest;
import com.togedong.auth.controller.dto.SignUpRequest;
import com.togedong.auth.controller.dto.TokenResponse;
import com.togedong.auth.controller.dto.UserResponse;
import com.togedong.member.entity.Member;

public interface AuthService {

    UserResponse register(final SignUpRequest request);

    TokenResponse login(final SignInRequest request);

    Member findMemberByJwt(final String jwt);
}
