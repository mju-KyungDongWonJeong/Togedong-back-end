package com.togedong.global.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private String secretKey = "eyJhbGciOiJIUzI1NiJ9eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5ODUxMjMwMSwiaWF0IjoxNjk4NTEyMzAxfQkMdVrwZRf8VyrDh0RH56LUQWZWM7eRKrzWm6MBqzvrk";

    private long expiredMillisecond = 604800000;

    public String createAccessToken(final String userFormId) {
        Claims claims = Jwts.claims()
            .setSubject(userFormId);

        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + expiredMillisecond);

        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claims)
            .setExpiration(accessTokenExpiresIn)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String getPayload(final String token) {
        Jws<Claims> claim = parseClaims(token);
        return claim.getBody()
            .getSubject();
    }

    public Jws<Claims> parseClaims(final String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }
}
