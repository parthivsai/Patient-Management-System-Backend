package com.patientmanagement.main;

import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("scope", role);
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        Date now = new Date();

        return Jwts.builder()
        		.setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String resolveToken(HttpServletRequest request) {
    	Enumeration<String> headerNames = request.getHeaderNames();
//    	while (headerNames.hasMoreElements()) {
//    	    String headerName = headerNames.nextElement();
//    	    String headerValue = request.getHeader(headerName);
//    	    System.out.println("Header: " + headerName + ", Value: " + headerValue);
//    	}
        String bearerToken = request.getHeader("authorization");
//        System.out.println("btoken " + bearerToken + " req" + request.getHeaderNames());
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	System.out.println(bearerToken);
        	System.out.println(bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }

//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String username = claims.get("sub", String.class);
        String role = claims.get("scope", String.class);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        User principal = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}