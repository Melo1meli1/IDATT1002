package no.idatt1002.repository;

import no.idatt1002.AppState;
import no.idatt1002.Model.Expense;
import no.idatt1002.service.config.MonthYearRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Repository class for Expense
 */
public class ExpenseRepository {

    private AppState appState;

    public ExpenseRepository() {
        appState = AppState.getInstance();
    }

    /**
     * This method sends an HTTP GET request to the expense endpoint with
     * authorization headers and
     * returns a List of the users Expenses for the current month
     * 
     * @return List of Expense objects
     */

    public List<Expense> getExpense() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/expence/current"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get expenses. Status code: " + response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Expense> expenses = objectMapper.readValue(response.body(), new TypeReference<List<Expense>>() {
            });

            return expenses;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method sends an HTTP GET request to the expense endpoint with
     * authorization headers and
     * returns a List of all of the users Expense objects
     * 
     * @return List of Expense objects
     */
    public List<Expense> getAllExpences() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/expence"))
                // add header with authe
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Failed to get expenses. Status code: " + response.statusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Expense> expenses = objectMapper.readValue(response.body(), new TypeReference<List<Expense>>() {
            });

            return expenses;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method sends an HTTP POST request to the expense endpoint with
     * authorization headers and
     * adds a new Expense for the user in the database
     * 
     * @param expense Expense object to create
     */
    public void addExpense(Expense expense) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/expence"))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers
                        .ofString(
                                "{\"name\": \"" + expense.getName()
                                        + "\", \"category\": \"" + expense.getCategory()
                                        + "\", \"sum\": \"" + expense.getSum()
                                        + "\", \"recurring\": \"" + expense.isRecurring()
                                        + "\", \"date\": \"" + expense.getDate() + "\"}"))
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
     * This method sends an HTTP PATCH request to the expence endpoint with
     * authorization headers and updates a Expence object given the id.
     * 
     * @param expense Expense object to update
     * @return boolean true if successful, false if not
     */
    public boolean updateExpence(Expense expense) {
        System.out.println(appState.getJwtToken());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/expence/" + expense.getId()))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers
                        .ofString(
                                "{\"id\": \"" + expense.getId()
                                        + "\", \"name\": \"" + expense.getName()
                                        + "\", \"category\": \"" + expense.getCategory()
                                        + "\", \"sum\": \"" + expense.getSum()
                                        + "\", \"recurring\": \"" + expense.isRecurring()
                                        + "\", \"date\": \"" + expense.getDate() + "\"}"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            System.out.println("response: " + response.statusCode());
            return true;
            // return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Expense> getMonthYear(MonthYearRequest monthYear) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/expence/monthyear"))
                .header("Authorization", "Bearer " + appState.getJwtToken())
                .header("Content-Type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.ofString(
                        "{\"month\": \"" + monthYear.getMonth()
                                + "\", \"year\": \"" + monthYear.getYear() + "\"}"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception(
                        "Failed to get expenses. Status code: " + response.statusCode() + " " + response.body());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<Expense> expenses = objectMapper.readValue(response.body(), new TypeReference<List<Expense>>() {
            });

            return expenses;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
