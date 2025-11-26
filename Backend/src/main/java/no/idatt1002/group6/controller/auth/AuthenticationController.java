package no.idatt1002.group6.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import no.idatt1002.group6.config.AuthenticationRequest;
import no.idatt1002.group6.config.AuthenticationResponse;
import no.idatt1002.group6.config.RegisterRequest;
import no.idatt1002.group6.service.auth.AuthenticationService;

/**
 * The AuthenticationController class defines endpoints for the authentication
 * related operations
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService aService;

    /**
     * Registers a new user with the given registration details.
     *
     * @param request The user's registration details.
     * @return An AuthenticationResponse object containing the user's authentication
     *         token.
     */
    @PostMapping("/register")
    @Operation(summary = "Registers a new user", description = "Registers a new user with the given registration details.")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(aService.register(request));
    }

    /**
     * Authenticates an existing user with the given authentication details.
     *
     * @param request The user's authentication details.
     * @return An AuthenticationResponse object containing the user's authentication
     *         token.
     */
    @PostMapping("/authenticate")
    @Operation(summary = "Authenticates a user", description = "Authenticates an existing user with the given authentication details.")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(aService.authenticate(request));
    }

}
