package no.idatt1002.group6.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.idatt1002.group6.config.AuthenticationRequest;
import no.idatt1002.group6.config.AuthenticationResponse;
import no.idatt1002.group6.config.RegisterRequest;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.UserRepository;

/**
 * Service class for handling authentication-related operations
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

        /**
         * The repository for accessing user data.
         */
        private final UserRepository uRepository;

        /**
         * The encoder for encoding user passwords.
         */
        private final PasswordEncoder passwordEncoder;

        /**
         * The service for generating JSON web tokens.
         */
        private final JwtService jwtService;

        /**
         * The authentication manager for authenticating users.
         */
        private final AuthenticationManager authenticationManager;

        /**
         * Registers a new user with the specified details.
         *
         * @param request the details of the user to be registered
         * @return the authentication response containing the generated JSON web token
         */
        public AuthenticationResponse register(RegisterRequest request) {

                var user = User.builder()
                                .name(request.getName())
                                .age(request.getAge())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .phone(request.getPhone())
                                .address(request.getAddress())
                                .build();
                uRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).build();
        }

        /**
         * Authenticates a user with the specified credentials.
         *
         * @param request the authentication request containing the user's email and
         *                password
         * @return the authentication response containing the generated JSON web token
         * @throws RuntimeException if the user is not found
         */
        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = uRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

}
