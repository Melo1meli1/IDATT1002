package no.idatt1002.repository;

import no.idatt1002.AppState;
import no.idatt1002.Model.Income;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Repository class for Income
 */
public class IncomeRepository {
    private AppState appState;

    public IncomeRepository() {
        appState = AppState.getInstance();
    }

    /**
     * This method sends an HTTP GET request to the income endpoint with
     * authorization headers and
     * returns a List of the users income for the current month
     * 
     * @return List of income objects
     */

    public List<Income> getIncome() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/income/current"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get incomes. Status code: " + response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Income> incomes = objectMapper.readValue(response.body(), new TypeReference<List<Income>>() {
            });

            return incomes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method sends an HTTP GET request to the income endpoint with
     * authorization headers and
     * returns a List of all of the users income objects
     * 
     * @return List of income objects
     */
    public List<Income> getAllIncome() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/income"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get incomes. Status code: " + response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Income> incomes = objectMapper.readValue(response.body(), new TypeReference<List<Income>>() {
            });

            return incomes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method sends an HTTP POST request to the income endpoint with
     * authorization headers and
     * adds a new income for the user in the database
     * 
     * @param income income object to create
     */
    public void addIncome(Income income) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/income"))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers
                        .ofString(
                                "{\"name\": \"" + income.getName()
                                        + "\", \"category\": \"" + income.getCategory()
                                        + "\", \"sum\": \"" + income.getSum()
                                        + "\", \"recurring\": \"" + income.isRecurring()
                                        + "\", \"date\": \"" + income.getDate() + "\"}"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            // return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends an HTTP PATCH request to the income endpoint with
     * authorization headers and updates a income object given the id.
     * 
     * @param income income object to update
     * @return true if successful, false if not
     */
    public boolean updateIncome(Income income) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/income/" + income.getId()))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers
                        .ofString(
                                "{\"name\": \"" + income.getName()
                                        + "\", \"category\": \"" + income.getCategory()
                                        + "\", \"sum\": \"" + income.getSum()
                                        + "\", \"recurring\": \"" + income.isRecurring()
                                        + "\", \"date\": \"" + income.getDate() + "\"}"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

}
