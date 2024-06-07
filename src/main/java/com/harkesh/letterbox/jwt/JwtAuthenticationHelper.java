package com.harkesh.letterbox.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationHelper {
    private final String JWT_SECRET = "thnthenithcthe88983nt898othe8388738198tot3389ao0901tbxwqwvcthwmwmwve88exxvzwjvqpynn";

    private final long JWT_TOKEN_VALIDITY = 60 * 60;

    public String getUsernameFromToken(String token) {
        Claims claims = this.getClaimsFromToken(token);
        return claims.getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return (Claims) Jwts.parser()
            .verifyWith((SecretKey) getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload(); //           .parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = this.getClaimsFromToken(token);
        Date expiryDate = claims.getExpiration();
        return expiryDate.before(new Date());
    }

    private Key getSigningKey() {
        byte[] key = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000)))
            .signWith(getSigningKey())
            .compact();
    }

}
