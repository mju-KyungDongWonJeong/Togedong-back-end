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

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expired}")
    private long expiredMillisecond;

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
