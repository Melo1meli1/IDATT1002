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

import jakarta.validation.Valid;
import no.idatt1002.group6.config.DateRequest;
import no.idatt1002.group6.config.MonthYearRequest;
import no.idatt1002.group6.model.Income;
import no.idatt1002.group6.service.IncomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * The IncomeController class defines endpoints for the income related
 * operations
 */
@RestController
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    /**
     * Retrieves all income objects for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's income objects and
     *         HTTP status code 200 (OK)
     */
    @GetMapping("/income")
    @Operation(summary = "Get all income objects", description = "Retrieves all income objects for the authenticated user")
    public ResponseEntity<List<Income>> getIncome(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(incomeService.getIncome(authentication));
    }

    /**
     * Creates a new income object for the authenticated user
     *
     * @param income         the income object to be created
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the newly created income object and HTTP status
     *         code 200 (OK)
     */
    @PostMapping("/income")
    @Operation(summary = "Create an income object", description = "Creates a new income object for the authenticated user")
    public ResponseEntity<Income> createIncome(@RequestBody Income income,
            @Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(incomeService.createIncome(income, authentication));
    }

    /**
     * Updates an existing income object for the authenticated user
     *
     * @param income_id      the id of the income object to be updated
     * @param income         the income object with updated data
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the updated income object and HTTP status code
     *         200 (OK)
     */
    @PatchMapping("/income/{id}")
    @Operation(summary = "Update an income object", description = "Updates an existing income object for the authenticated user")
    public ResponseEntity<Income> updateIncome(@PathVariable("id") int income_id, @RequestBody Income income,
            @Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(incomeService.updateIncome(income_id, income, authentication));
    }

    /**
     * Deletes an existing income object for the authenticated user
     *
     * @param income_id      the id of the income object to be deleted
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with HTTP status code 200 (OK)
     */
    @DeleteMapping("/income/{id}")
    @Operation(summary = "Delete an income object", description = "Deletes an existing income object for the authenticated user")
    public ResponseEntity<HttpStatus> deleteIncome(@PathVariable("id") int income_id,
            @Parameter(hidden = true) Authentication authentication) {
        incomeService.deleteIncome(income_id, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Retrieves all income objects for a specified month and year for the
     * authenticated user
     *
     * @param monthYearRequest the request object containing the month and year to
     *                         retrieve income objects for
     * @param authentication   the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's income objects for the
     *         specified month and year and HTTP status code 200 (OK)
     */
    @GetMapping("/income/monthyear")
    @Operation(summary = "Get income objects for a specified month and year", description = "Retrieves all income objects for a specified month and year for the authenticated user")
    public ResponseEntity<List<Income>> getMonthlyIncome(@RequestBody MonthYearRequest monthYearRequest,
            Authentication authentication) {

        return ResponseEntity.ok(incomeService.getIncomeMonthYear(monthYearRequest, authentication));

    }

    /**
     * Retrieves all income objects for a specified date for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @param date           the date object to retrieve income objects for
     * @return a ResponseEntity with a list of all the user's income objects for the
     *         specified date and HTTP status code 200 (OK)
     */
    @GetMapping("/income/date")
    @Operation(summary = "Get income objects for a specified date", description = "Retrieves all income objects for a specified date for the authenticated user")
    public ResponseEntity<List<Income>> getIncomeDate(@Parameter(hidden = true) Authentication authentication,
            @Valid @RequestBody DateRequest date) {

        return ResponseEntity.ok(incomeService.getIncomeDate(date, authentication));
    }

    /**
     * Retrieves the total amount of income for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the total amount of the user's income and HTTP
     *         status code 200 (OK)
     */
    @GetMapping("/income/total")
    @Operation(summary = "Get total income", description = "Retrieves the total amount of income for the authenticated user")
    public ResponseEntity<Integer> getTotalIncome(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(incomeService.getTotalIncome(authentication));
    }

    /**
     * Retrieves all income objects for the current month for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's income objects for the
     *         current month and HTTP status code 200 (OK)
     */
    @GetMapping("/income/current")
    @Operation(summary = "Get current month income", description = "Retrieves all income objects for the current month for the authenticated user")
    public ResponseEntity<List<Income>> getCurrentIncome(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(incomeService.getCurrentIncome(authentication));
    }

}