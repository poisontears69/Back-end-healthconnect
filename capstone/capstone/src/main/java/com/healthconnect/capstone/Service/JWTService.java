package com.healthconnect.capstone.Service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;

import com.healthconnect.capstone.Variables.Role;

@Service
public class JWTService {
	
	private final String SECRET_KEY = "5da1ee16fc220c03fb3634519af304d61a77c25471c4fdfc97c78299dcfc7d0e";
	
	public Boolean isValid(String token, UserDetails user) {
		String username = extractUsername(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//	changed from userEntity to UserDetails so that it can accept User and Admin
	public String generateToken(UserDetails user, Role role) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();
        return token;
    }
	
	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
