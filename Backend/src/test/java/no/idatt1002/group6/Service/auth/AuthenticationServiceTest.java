package no.idatt1002.group6.Service.auth;

import no.idatt1002.group6.config.AuthenticationRequest;
import no.idatt1002.group6.config.AuthenticationResponse;
import no.idatt1002.group6.config.RegisterRequest;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.UserRepository;
import no.idatt1002.group6.service.auth.AuthenticationService;
import no.idatt1002.group6.service.auth.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void registerTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Jan roger");
        registerRequest.setAge(30);
        registerRequest.setEmail("roger@gmail.com");
        registerRequest.setPassword("password123");
        registerRequest.setPhone("123456789");
        registerRequest.setAddress("Trondheim");

        User user = User.builder()
                .name(registerRequest.getName())
                .age(registerRequest.getAge())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt_token");

        AuthenticationResponse response = authService.register(registerRequest);

        assertEquals("jwt_token", response.getToken());
    }

    @Test
    public void authenticateTest() {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("roger@gmail.com");
        authRequest.setPassword("password123");

        User user = User.builder()
                .name("Jan roger")
                .age(30)
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .phone("123456789")
                .address("Trondheim")
                .build();

        when(userRepository.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt_token");

        AuthenticationResponse response = authService.authenticate(authRequest);

        assertEquals("jwt_token", response.getToken());
    }

}
