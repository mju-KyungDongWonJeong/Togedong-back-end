package com.togedong.global.annotation;

import com.togedong.auth.service.AuthService;
import com.togedong.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class TokenInfoResolver implements HandlerMethodArgumentResolver {

    private static final int BEARER_TOKEN_PREFIX_LENGTH = 7;
    private static final String AUTHORIZATION_REQUEST_HEADER = "Authorization";

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenInfo.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(AUTHORIZATION_REQUEST_HEADER);
        String jwt = getJwtFromAuthorization(authorization);

        return authService.findMemberByJwt(jwt);
    }

    private String getJwtFromAuthorization(String authorization) {
        return authorization.substring(BEARER_TOKEN_PREFIX_LENGTH);
    }
}
