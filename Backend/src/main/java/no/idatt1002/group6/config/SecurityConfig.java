package no.idatt1002.group6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for setting up Spring Security in the application. This
 * class enables web security and provides a security filter chain for
 * protecting endpoints, authenticating requests, and authorizing access.
 * 
 * @see org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 * @see org.springframework.security.web.SecurityFilterChain
 * @see org.springframework.security.authentication.AuthenticationProvider
 * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
 * @see org.springframework.security.config.http.SessionCreationPolicy
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Authentication provider for authenticating user credentials against the
     * database.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * JWT authentication filter for handling requests with JWT tokens.
     */
    private final JwtAuthentificationFiler jwtAuthFilter;

    /**
     * Configures the security filter chain to protect endpoints, authenticate
     * requests, and authorize access based on user roles.
     * 
     * @param http the HTTP security object used to configure the security filter
     *             chain
     * @return a security filter chain for protecting endpoints, authenticating
     *         requests, and authorizing access
     * @throws Exception if an error occurs while configuring the security filter
     *                   chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
