package no.idatt1002.group6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.service.UserService;

/**
 * The UserController class defines endpoints for the user related operations
 */
@RestController
@Tag(name = "User", description = "Endpoints for the user related operations")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    /**
     * Retrieves the name and version of the application
     *
     * @return a String containing the name and version of the application
     */
    @GetMapping("/app")
    @Operation(summary = "Get application details", description = "Retrieves the name and version of the application")
    public String getAppDetails() {
        return appName + " " + appVersion;
    }

    /**
     * Retrieves the user object for the authenticated user
     *
     * @return a ResponseEntity with the user object for the authenticated user and
     *         HTTP status code 200 (OK)
     */
    @GetMapping("/user")
    @Operation(summary = "Get user details", description = "Retrieves the user object for the authenticated user")
    public ResponseEntity<User> getUser(
            @Parameter(hidden = true) org.springframework.security.core.Authentication authentication) {
        return ResponseEntity.ok(userService.getUser());
    }

}
