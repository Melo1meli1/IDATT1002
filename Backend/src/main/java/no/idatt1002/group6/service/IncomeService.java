package no.idatt1002.group6.service;

import java.util.List;
import org.springframework.security.core.Authentication;

import no.idatt1002.group6.config.DateRequest;
import no.idatt1002.group6.config.MonthYearRequest;
import no.idatt1002.group6.model.Income;

/**
 * The IncomeService interface defines methods for managing budgets
 */

public interface IncomeService {

    /**
     * Retrieves a list of all incomes for the authenticated user.
     *
     * @param authentication the current authentication object
     * @return a list of incomes for the user
     */
    List<Income> getIncome(Authentication authentication);

    /**
     * Retrieves a list of current month incomes for the authenticated user.
     *
     * @param authentication the current authentication object
     * @return a list of current incomes for the user
     */
    List<Income> getCurrentIncome(Authentication authentication);

    /**
     * Creates a new income for the authenticated user.
     *
     * @param income         the income to be created
     * @param authentication the current authentication object
     * @return the created income
     */
    Income createIncome(Income income, Authentication authentication);

    /**
     * Updates an existing income for the authenticated user.
     *
     * @param income_id      the id of the income to be updated
     * @param income         the updated income
     * @param authentication the current authentication object
     * @return the updated income
     */
    Income updateIncome(int income_id, Income income, Authentication authentication);

    /**
     * Deletes an existing income for the authenticated user.
     *
     * @param income_id      the id of the income to be deleted
     * @param authentication the current authentication object
     * @return the deleted income
     */
    Income deleteIncome(int income_id, Authentication authentication);

    /**
     * Retrieves a list of all incomes for the authenticated user for a given month
     * and year.
     *
     * @param monthyear      the month and year to filter the incomes
     * @param authentication the current authentication object
     * @return a list of incomes for the user for the given month and year
     */
    List<Income> getIncomeMonthYear(MonthYearRequest monthyear, Authentication authentication);

    /**
     * Retrieves a list of all incomes for the authenticated user for a given date.
     *
     * @param date           the date to filter the incomes
     * @param authentication the current authentication object
     * @return a list of incomes for the user for the given date
     */
    List<Income> getIncomeDate(DateRequest date, Authentication authentication);

    /**
     * Calculates the total income for the authenticated user.
     *
     * @param authentication the current authentication object
     * @return the total income for the user
     */

    int getTotalIncome(Authentication authentication);

}
