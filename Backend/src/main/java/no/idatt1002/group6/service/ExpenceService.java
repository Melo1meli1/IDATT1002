package no.idatt1002.group6.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import no.idatt1002.group6.config.DateRequest;
import no.idatt1002.group6.config.MonthYearRequest;
import no.idatt1002.group6.model.Expences;

public interface ExpenceService {

    /**
     * Retrieves all expenses for the authenticated user.
     * 
     * @param authentication the current authentication object
     * @return a list of all expenses for the user
     */
    List<Expences> getExpence(Authentication authentication);

    /**
     * Creates a new expense for the authenticated user.
     * 
     * @param expence        the expense to be created
     * @param authentication the current authentication object
     * @return the created expense
     */
    Expences createExpence(Expences expence, Authentication authentication);

    /**
     * Updates an existing expense for the authenticated user.
     * 
     * @param expence_id     the id of the expense to be updated
     * @param expence        the updated expense
     * @param authentication the current authentication object
     * @return the updated expense
     */
    Expences updateExpence(int expence_id, Expences expence, Authentication authentication);

    /**
     * Deletes an existing expense for the authenticated user.
     * 
     * @param expence_id     the id of the expense to be deleted
     * @param authentication the current authentication object
     * @return the deleted expense
     */
    Expences deleteExpence(int expence_id, Authentication authentication);

    /**
     * Retrieves expenses for the authenticated user within a given month and year.
     * 
     * @param monthyear      the month and year to retrieve expenses for
     * @param authentication the current authentication object
     * @return a list of expenses for the user within the given month and year
     */
    List<Expences> getExpenceMonthYear(MonthYearRequest monthyear, Authentication authentication);

    /**
     * Retrieves expenses for the authenticated user on a given date.
     * 
     * @param date           the date to retrieve expenses for
     * @param authentication the current authentication object
     * @return a list of expenses for the user on the given date
     */
    List<Expences> getExpenceDate(DateRequest date, Authentication authentication);

    /**
     * Retrieves the total amount of expenses for the authenticated user.
     * 
     * @param authentication the current authentication object
     * @return the total amount of expenses for the user
     */
    int getTotalExpence(Authentication authentication);

    /**
     * Retrieves the current month's expenses for the authenticated user.
     * 
     * @param authentication the current authentication object
     * @return a list of expenses for the user in the current month
     */
    List<Expences> getCurrentExpence(Authentication authentication);

}
