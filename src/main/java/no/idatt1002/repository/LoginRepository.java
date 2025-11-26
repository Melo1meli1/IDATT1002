package no.idatt1002.repository;

import no.idatt1002.Model.Token;
import no.idatt1002.service.config.LoginRequest;
import no.idatt1002.service.config.RegisterRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;

/**
 * Repository class for Login
 */
public class LoginRepository {

    /**
     * This method sends an HTTP POST request to the login endpoint with
     * authorization headers and
     * returns a bearer token
     * 
     * @return Token object
     */
    public Token login(LoginRequest user) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/authenticate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers
                        .ofString("{\"email\": \"" + user.getEmail() + "\", \"password\": \"" + user.getPassword()
                                + "\"}"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            org.json.JSONObject obj = new org.json.JSONObject(response.body());

            if (response.statusCode() == 200) {

                Token token = new Token(obj.getString("token"));
                return token;
            } else {

                return null;
            }

            // return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * This method sends an HTTP POST request to the register endpoint with
     * authorization headers and
     * returns a bearer token
     * 
     * @return Token object
     */
    public Token register(RegisterRequest user) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers
                        .ofString("{\"age\": \"" + user.getAge() + "\", \"name\": \"" + user.getName()
                                + "\", \"email\": \"" + user.getEmail() + "\", \"password\": \"" + user.getPassword()
                                + "\", \"phone\": \"" + user.getPhone() + "\", \"address\": \"" + user.getAddress()
                                + "\"}"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            org.json.JSONObject obj = new org.json.JSONObject(response.body());

            if (response.statusCode() == 200) {
                Token token = new Token(obj.getString("token"));
                return token;
            } else {
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
