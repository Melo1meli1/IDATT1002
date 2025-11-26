package no.idatt1002.group6.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import no.idatt1002.group6.model.Budget;

/**
 * The BudgetService interface defines methods for managing budgets.
 */
public interface BudgetService {

    /**
     * Retrieves a list of all budgets for the authenticated user.
     *
     * @param authentication the current authentication object
     * @return a list of budgets for the user
     */
    List<Budget> getBudget(Authentication authentication);

    /**
     * Creates a new budget for the authenticated user.
     *
     * @param budget         the budget to be created
     * @param authentication the current authentication object
     * @return the created budget
     */
    Budget createBudget(Budget budget, Authentication authentication);

    /**
     * Updates an existing budget for the authenticated user.
     *
     * @param id             the id of the budget to be updated
     * @param budget         the updated budget
     * @param authentication the current authentication object
     * @throws ResourceNotFoundException if the budget is not found
     */
    Budget updateBudget(int id, Budget budget, Authentication authentication);

    /**
     * Deletes an existing budget for the authenticated user.
     *
     * @param id             the id of the budget to be deleted
     * @param authentication the current authentication object
     * @return the deleted budget
     * @throws ResourceNotFoundException if the budget is not found
     */
    Budget deleteBudget(int id, Authentication authentication);

}
