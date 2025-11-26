package no.idatt1002.group6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import no.idatt1002.group6.repository.UserRepository;

/**
 * Configuration class for setting up Spring Security in the application.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository uRepository;

    /**
     * Creates a new instance of the {@link UserDetailsService} bean.
     * This method will be called by Spring to create the bean.
     * 
     * @return The {@link UserDetailsService} bean.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> uRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Creates a new instance of the {@link PasswordEncoder} bean.
     * This method will be called by Spring to create the bean.
     * 
     * @return The {@link PasswordEncoder} bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a new instance of the {@link AuthenticationProvider} bean.
     * This method will be called by Spring to create the bean.
     * 
     * @return The {@link AuthenticationProvider} bean.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates a new instance of the {@link AuthenticationManager} bean.
     * This method will be called by Spring to create the bean.
     * 
     * @param config The {@link AuthenticationConfiguration} configuration object.
     * @return The {@link AuthenticationManager} bean.
     * @throws Exception If there is an error creating the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
