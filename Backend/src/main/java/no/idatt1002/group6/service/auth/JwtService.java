package no.idatt1002.group6.service.auth;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service for handling JWT token generation and verification.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Extracts the user email from a JWT token.
     * 
     * @param Token the JWT token
     * @return the email of the user associated with the token
     */
    public String extractUserEmail(String Token) {
        return extractClaim(Token, Claims::getSubject);
    }

    /**
     * Extracts a claim from a JWT token using a provided claim resolver.
     * 
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the claim from the token
     * @param <T>            the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a user.
     * 
     * @param userDetails the user details
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for a user with additional claims.
     * 
     * @param extraClaims  the additional claims to include in the token
     * @param userDetailes the user details
     * @return the generated JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetailes) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetailes.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * Checks if a JWT token is valid for a user.
     * 
     * @param token       the JWT token to verify
     * @param userDetails the user details
     * @return true if the token is valid for the user, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT token has expired.
     * 
     * @param token the JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(0));
    }

    /**
     * Extracts the expiration date from a JWT token.
     * 
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private java.util.Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from a JWT
     * 
     * @param token the JWT token
     * @return all claims from the token
     * 
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 
     * Returns a Key object used to sign JWT tokens. It decodes the secret key from
     * base64 and uses it to create an HMAC SHA key.
     * 
     * @return the Key object used to sign JWT tokens.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
