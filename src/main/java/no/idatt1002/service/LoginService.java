package no.idatt1002.service;

import no.idatt1002.AppState;
import no.idatt1002.Model.Token;
import no.idatt1002.repository.Cache;
import no.idatt1002.repository.LoginRepository;
import no.idatt1002.service.config.LoginRequest;
import no.idatt1002.service.config.RegisterRequest;

/**
 * Service class for handling user authentication and registration.
 */
public class LoginService {

    private LoginRepository repository = new LoginRepository();
    private UserService userService = new UserService();
    private AppState appState;

    public LoginService() {
        appState = AppState.getInstance();
    }

    /**
     * Sends a login request to the repository using the provided login credentials
     * and returns
     * a boolean indicating whether the request was successful or not.
     *
     * @param user The login request containing the user's email and password.
     * @return A boolean indicating whether the login request was successful or not.
     */
    public boolean login(LoginRequest user) {

        Token token = repository.login(user);
        if (token != null) {
            appState.setJwtToken(token.getToken());
            Cache cache = appState.getCache();
            cache.setUser("user", userService.getUser());

            return true;
        }
        return false;
    }

    /**
     * Sends a registration request to the repository using the provided user
     * information and returns
     * a boolean indicating whether the request was successful or not.
     *
     * @param user The registration request containing the user's name, email, and
     *             password.
     * @return A boolean indicating whether the registration request was successful
     *         or not.
     */
    public boolean register(RegisterRequest user) {

        Token token = repository.register(user);
        if (token != null) {
            appState.setJwtToken(token.getToken());
            Cache cache = appState.getCache();
            cache.setUser("user", userService.getUser());

            return true;
        }
        return false;

    }

}
