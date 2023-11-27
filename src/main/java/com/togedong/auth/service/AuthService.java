package com.togedong.auth.service;

import com.togedong.auth.controller.dto.SignInRequest;
import com.togedong.auth.controller.dto.SignUpRequest;
import com.togedong.auth.controller.dto.SignInResponse;
import com.togedong.auth.controller.dto.UserResponse;
import com.togedong.member.entity.Member;

public interface AuthService {

    UserResponse register(final SignUpRequest request);

    SignInResponse login(final SignInRequest request);

    Member findMemberByJwt(final String jwt);
}
