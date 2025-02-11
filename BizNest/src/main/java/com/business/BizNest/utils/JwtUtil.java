package com.business.BizNest.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private String SECRET_KEY = "Tak+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
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
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserNameFromJwtToken(String token){
        return extractALlClaims(token).getSubject();
    }

    public Claims extractALlClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractALlClaims(token).getSubject();
    }


}
