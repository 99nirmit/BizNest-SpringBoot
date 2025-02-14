package com.business.BizNest.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationsMs ;

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getJwtFromHeader(HttpServletRequest request){
        System.out.println(request  + "request getting");
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        logger.debug("Authorization Header: {}", bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateToken(UserDetails userDetails){
        String username = userDetails.getUsername();
        Map<String, Object> claims  = new HashMap<>();
        return generateTokenFromUsername(claims, username);
    }

    private String generateTokenFromUsername(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .header().empty().add("typ", "jwt")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(key())
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            System.out.println("Validate");
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
            return true;
        } catch (JwtException e) {
            throw new RuntimeException("JWT Validation failed: " + e.getMessage(), e);
        }
    }

    public String getUserNameFromJwtToken(String token){
        return extractALlClaims(token).getSubject();
    }

    public Claims extractALlClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extractALlClaims(token).getSubject();
    }

}
