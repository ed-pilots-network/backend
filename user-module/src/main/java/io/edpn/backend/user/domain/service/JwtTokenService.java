package io.edpn.backend.user.domain.service;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String extractUsername(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);

    Boolean isTokenExpired(String token);

    String generateToken(UserDetails userDetails);

    String createToken(Map<String, Object> claims, String subject);

    Boolean validateToken(String token, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String createRefreshToken(Map<String, Object> claims, String subject);
}
