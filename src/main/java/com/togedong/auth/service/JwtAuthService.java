package com.togedong.auth.service;

import com.togedong.auth.dto.SignInRequest;
import com.togedong.auth.dto.SignUpRequest;
import com.togedong.auth.dto.TokenResponse;
import com.togedong.auth.dto.UserResponse;
import com.togedong.global.helper.JwtProvider;
import com.togedong.user.entity.User;
import com.togedong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtAuthService implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public UserResponse register(final SignUpRequest request) {
        validateDuplicateUserId(request.userId());

        User user = User.builder()
            .userId(request.userId())
            .userName(request.userName())
            .password(request.password())
            .build();

        return userRepository.save(user)
            .toDto();
    }

    @Override
    @Transactional
    public TokenResponse login(final SignInRequest request) {
        User user = userRepository.findByUserId(request.userId())
            .orElseThrow();

        return null;
    }

    private void validateDuplicateUserId(final String userId) {
        if (!userRepository.existsByUserId(userId)) {

        }
    }
}
