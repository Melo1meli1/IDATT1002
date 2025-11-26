package no.idatt1002.repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import no.idatt1002.AppState;
import no.idatt1002.Model.Budget;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Repository class for Budget
 */
public class BudgetRepository {
    private AppState appState;

    /**
     * Constructor for BudgetRepository
     * Initializes the AppState
     */
    public BudgetRepository() {
        appState = AppState.getInstance();
    }

    /**
     * This method sends an HTTP GET request to the budget endpoint with
     * authorization headers
     * and returns a list of Budget objects parsed from the response body.
     * 
     * @return The method is returning a List of Budget objects.
     */
    public List<Budget> getBudget() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/budget"))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get budget. Status code: " + response.statusCode());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List<Budget> budget = objectMapper.readValue(response.body(), new TypeReference<List<Budget>>() {
            });

            return budget;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This function adds a budget to a server using HTTP POST request with
     * authorization and JSON
     * data.
     * 
     * @param budget The budget object
     */
    public void addBudget(Budget budget) {
        int budgetIeInt = budget.getIe() ? 1 : 0;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create("http://localhost:8080/api/v1/budget"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers
                        .ofString("{\"category\": \"" + budget.getCategory() + "\", \"sum\": \"" + budget.getSum()
                                + "\", \"user_id\": \"" + appState.getCache().getUser("user").getId()
                                + "\", \"ie\": \"" + budgetIeInt
                                + "\", \"priority\": \"" + budget.getPriority()
                                + "\"}"))
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to add budget. Status code: " + response.statusCode() + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function updates a budget object by sending a PATCH request to the
     * budget endpoint
     * 
     * @param budget The budget object that contains the updated information for a
     *               budget.
     * @return A boolean value of true is being returned.
     */
    public boolean updateBudget(Budget budget) {

        int budgetIeInt = budget.getIe() ? 1 : 0;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create("http://localhost:8080/api/v1/budget/" + budget.getId()))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers
                        .ofString("{\"category\": \"" + budget.getCategory() + "\", \"sum\": \"" + budget.getSum()
                                + "\", \"user_id\": \"" + appState.getCache().getUser("user").getId()
                                + "\", \"ie\": \"" + budgetIeInt
                                + "\", \"priority\": \"" + budget.getPriority()
                                + "\"}"))
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to update budget. Status code: " + response.statusCode() + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
