package com.togedong.auth.controller;

import com.togedong.auth.dto.SignInRequest;
import com.togedong.auth.dto.SignUpRequest;
import com.togedong.auth.dto.TokenResponse;
import com.togedong.auth.dto.UserResponse;
import com.togedong.auth.service.AuthService;
import com.togedong.global.response.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Object> register(@RequestBody @Valid final SignUpRequest request) {
        UserResponse response = authService.register(request);
        return ResponseHandler
            .generateResponse("회원가입에 성공했습니다.", HttpStatus.CREATED, response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> login(@RequestBody @Valid final SignInRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseHandler
            .generateResponse("로그인에 성공했습니다.", HttpStatus.OK, response);
    }
}