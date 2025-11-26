package no.idatt1002.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import no.idatt1002.AppState;
import no.idatt1002.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class for managing user objects
 */
public class UserService {

    /**
     * Returns the currently authenticated user.
     *
     * @return the currently authenticated user
     * @throws RuntimeException if an error occurs while getting the user from the
     *                          server
     */
    public User getUser() {
        AppState appState = AppState.getInstance();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/user"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get user. Status code: " + response.statusCode());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(response.body(), User.class);

            return user;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
