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
import no.idatt1002.group6.model.Expences;
import no.idatt1002.group6.service.ExpenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * The ExpenceController class defines endpoints for the expence related
 * operations
 */

@RestController
public class ExpenceController {

    @Autowired
    private ExpenceService eService;

    /**
     * Retrieves all expences for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's expences and HTTP
     *         status code 200 (OK)
     */
    @GetMapping("/expence")
    @Operation(summary = "Get all expences", description = "Retrieves all expences for the authenticated user")
    public ResponseEntity<List<Expences>> getIncome(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(eService.getExpence(authentication));
    }

    /**
     * Creates a new expence for the authenticated user
     *
     * @param expence        the expence object to be created
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the newly created expence and HTTP status code
     *         200 (OK)
     */
    @PostMapping("/expence")
    @Operation(summary = "Create an expence", description = "Creates a new expence for the authenticated user")
    public ResponseEntity<Expences> createIncome(@RequestBody Expences expence,
            @Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(eService.createExpence(expence, authentication));
    }

    /**
     * Updates an existing expence for the authenticated user
     *
     * @param expence_id     the id of the expence to be updated
     * @param expence        the expence object with updated data
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the updated expence and HTTP status code 200
     *         (OK)
     */
    @PatchMapping("/expence/{id}")
    @Operation(summary = "Update an expence", description = "Updates an existing expence for the authenticated user")
    public ResponseEntity<Expences> updateIncome(@PathVariable("id") int expence_id,
            @RequestBody Expences expence,
            @Parameter(hidden = true) Authentication authentication) {
        return ResponseEntity.ok(eService.updateExpence(expence_id, expence, authentication));
    }

    /**
     * Deletes an existing expence for the authenticated user
     *
     * @param expence_id     the id of the expence to be deleted
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with HTTP status code 200 (OK)
     */
    @DeleteMapping("/expence/{id}")
    @Operation(summary = "Delete an expence", description = "Deletes an existing expence for the authenticated user")
    public ResponseEntity<HttpStatus> deleteIncome(@PathVariable("id") int expence_id,
            @Parameter(hidden = true) Authentication authentication) {
        eService.deleteExpence(expence_id, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Retrieves all expences for a specified month and year for the authenticated
     * user
     *
     * @param monthYearRequest the request object containing the month and year to
     *                         retrieve expences for
     * @param authentication   the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's expences for the
     *         specified month and year and HTTP status code 200 (OK)
     */
    @GetMapping("/expence/monthyear")
    @Operation(summary = "Get expences for a specified month and year", description = "Retrieves all expences for a specified month and year for the authenticated user")
    public ResponseEntity<List<Expences>> getMonthlyIncome(@RequestBody MonthYearRequest monthYearRequest,
            Authentication authentication) {

        return ResponseEntity.ok(eService.getExpenceMonthYear(monthYearRequest, authentication));
        // authentication));

    }

    /**
     * Retrieves all expences for a specified date for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @param date           the date object to retrieve expences for
     * @return a ResponseEntity with a list of all the user's expences for the
     *         specified date and HTTP status code 200 (OK)
     */
    @GetMapping("/expence/date")
    @Operation(summary = "Get expences for a specified date", description = "Retrieves all expences for a specified date for the authenticated user")
    public ResponseEntity<List<Expences>> getIncomeDate(@Parameter(hidden = true) Authentication authentication,
            @Valid @RequestBody DateRequest date) {

        return ResponseEntity.ok(eService.getExpenceDate(date, authentication));
    }

    /**
     * Retrieves the total amount of expences for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with the total amount of the user's expences and
     *         HTTP status code 200 (OK)
     */
    @GetMapping("/expence/total")
    @Operation(summary = "Get total expences", description = "Retrieves the total amount of expences for the authenticated user")
    public ResponseEntity<Integer> getTotalExpence(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(eService.getTotalExpence(authentication));
    }

    /**
     * Retrieves all expences for the current month for the authenticated user
     *
     * @param authentication the authentication object for the current user
     * @return a ResponseEntity with a list of all the user's expences for the
     *         current month and HTTP status code 200 (OK)
     */
    @GetMapping("/expence/current")
    @Operation(summary = "Get current month expences", description = "Retrieves all expences for the current month for the authenticated user")
    public ResponseEntity<List<Expences>> getCurrentIncome(@Parameter(hidden = true) Authentication authentication) {

        return ResponseEntity.ok(eService.getCurrentExpence(authentication));
    }

}
