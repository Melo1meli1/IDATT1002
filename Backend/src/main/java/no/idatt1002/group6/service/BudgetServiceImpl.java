package no.idatt1002.group6.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import no.idatt1002.group6.model.Budget;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.BudgetRepository;
import no.idatt1002.group6.exeptions.ResourceNotFoundException;

/**
 * Service class for handling Expence-related operations
 */

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository bRepository;

    /**
     * Retrieves a list of all budgets for the authenticated user.
     * 
     * @param authentication the current authentication object
     * @return a list of budgets for the user
     */
    @Override
    public List<Budget> getBudget(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return bRepository.findAllByUserId(user.getId());
    }

    /**
     * Creates a new budget for the authenticated user.
     * 
     * @param budget         the budget to be created
     * @param authentication the current authentication object
     * @return the created budget
     */
    @Override
    public Budget createBudget(Budget budget, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        budget.setUser_id(user.getId());

        String category = budget.getCategory();
        category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
        budget.setCategory(category);

        return bRepository.save(budget);
    }

    /**
     * Updates an existing budget for the authenticated user.
     * 
     * @param id             the id of the budget to be updated
     * @param budget         the updated budget
     * @param authentication the current authentication object
     * @return the updated budget
     * @throws ResourceNotFoundException if the budget is not found
     */
    @Override
    public Budget updateBudget(int id, Budget budget, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Budget oldBudget = bRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        if (oldBudget.getUser_id() == user.getId()) {
            budget.setId(id);
            budget.setUser_id(user.getId());

            String category = budget.getCategory();
            category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
            budget.setCategory(category);

            return bRepository.save(budget);
        } else {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
    }

    /**
     * Deletes an existing budget for the authenticated user.
     * 
     * @param id             the id of the budget to be deleted
     * @param authentication the current authentication object
     * @return the deleted budget
     * @throws ResourceNotFoundException if the budget is not found
     */
    @Override
    public Budget deleteBudget(int id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Budget oldBudget = bRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        if (oldBudget.getUser_id() == user.getId()) {
            bRepository.delete(oldBudget);
            return oldBudget;
        } else {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
    }

}
