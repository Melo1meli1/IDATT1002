package no.idatt1002.group6.Service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import no.idatt1002.group6.service.auth.JwtService;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        userDetails = new org.springframework.security.core.userdetails.User(
                "john@example.com",
                "password123",
                new ArrayList<>());
    }

    @Test
    public void generateTokenAndExtractUserEmailTest() {
        String token = jwtService.generateToken(userDetails);
        String userEmail = jwtService.extractUserEmail(token);

        assertEquals(userDetails.getUsername(), userEmail);
    }

    @Test
    public void isTokenValidTest() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }
}
