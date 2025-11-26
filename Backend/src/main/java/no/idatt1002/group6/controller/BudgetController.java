package no.idatt1002.group6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.idatt1002.group6.model.Budget;
import no.idatt1002.group6.service.BudgetService;

/**
 * The BudgetController class defines endpoints for the budget related
 * operations
 */
@RestController
@Tag(name = "Budget", description = "Endpoints for the budget related operations")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    /**
     * Retrieves the list of all budget objects for the authenticated user
     *
     * @param authentication The authentication information of the user.
     * @return a ResponseEntity with the list of all budget objects for the
     *         authenticated user and HTTP status code 200 (OK)
     */
    @GetMapping("/budget")
    @Operation(summary = "Get all budgets", description = "Retrieves the list of all budget objects for the authenticated user")
    public ResponseEntity<List<Budget>> getBudget(Authentication authentication) {
        return ResponseEntity.ok(budgetService.getBudget(authentication));
    }

    /**
     * Creates a new budget object for the authenticated user
     *
     * @param budget         The budget information to create.
     * @param authentication The authentication information of the user.
     * @return a ResponseEntity with the created budget object and HTTP status code
     *         200 (OK)
     */
    @PostMapping("/budget")
    @Operation(summary = "Create a budget", description = "Creates a new budget object for the authenticated user")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget, Authentication authentication) {
        return ResponseEntity.ok(budgetService.createBudget(budget, authentication));
    }

    /**
     * Changes a budget object
     * 
     * @param id             The id of the budget to patch
     * @param budget         Request body of the budget
     * @param authentication The authentication information of the user.
     * @return a ResponseEntity with the updated budget object and HTTP status code
     *         200 (OK)
     */
    @PatchMapping("/budget/{id}")
    @Operation(summary = "Update a budget", description = "Changes a budget object")
    public ResponseEntity<Budget> updateBudget(@PathVariable int id, @RequestBody Budget budget,
            Authentication authentication) {
        return ResponseEntity.ok(budgetService.updateBudget(id, budget, authentication));
    }

    /**
     * Deletes a budget object
     * 
     * @param id             The id of the budget to delete
     * @param authentication The authentication information of the user.
     * @return a ResponseEntity with HTTP status code 200 (OK)
     */
    @DeleteMapping("/budget/{id}")
    @Operation(summary = "Delete a budget", description = "Deletes a budget object")
    public ResponseEntity<HttpStatus> deleteBudget(@PathVariable int id, Authentication authentication) {
        budgetService.deleteBudget(id, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
