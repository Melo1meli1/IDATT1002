package no.idatt1002.group6.service;

import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import no.idatt1002.group6.model.Income;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.IncomeRepository;
import no.idatt1002.group6.config.DateRequest;
import no.idatt1002.group6.config.MonthYearRequest;
import no.idatt1002.group6.exeptions.ResourceNotFoundException;

/**
 * Service class for handling Income-related operations
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository iRepository;

    /**
     * Returns a list of all incomes for the authenticated user
     * 
     * @param authentication the authentication object representing the current user
     * @return a list of Income objects
     */
    @Override
    public List<Income> getIncome(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return iRepository.findAllByUserId(user.getId());
    }

    /**
     * Returns a list of all incomes for the current month for the authenticated
     * user
     * 
     * @param authentication the authentication object representing the current user
     * @return a list of Income objects
     */
    @Override
    public List<Income> getCurrentIncome(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        LocalDate date = LocalDate.now();
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
        return iRepository.findIncomeByUserIdForCurrentMonth(user.getId(), startDate.toString(), date.toString());
    }

    /**
     * Creates a new income object for the authenticated user
     * 
     * @param income         the Income object to be created
     * @param authentication the authentication object representing the current user
     * @return the created Income object
     */
    @Override
    public Income createIncome(Income income, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        income.setUser_id(user.getId());

        String category = income.getCategory();
        category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
        income.setCategory(category);

        return iRepository.save(income);
    }

    /**
     * Updates an existing income object for the authenticated user
     * 
     * @param income_id      the id of the Income object to be updated
     * @param income         the updated Income object
     * @param authentication the authentication object representing the current user
     * @return the updated Income object
     * @throws ResourceNotFoundException if the Income object with the given id is
     *                                   not found
     */
    @Override
    public Income updateIncome(int income_id, Income income, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Income oldIncome = iRepository.findByIncome_Id(income_id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + income_id));
        if (oldIncome.getUser_id() == user.getId()) {
            income.setId(income_id);
            income.setUser_id(user.getId());

            String category = income.getCategory();
            category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
            income.setCategory(category);

            return iRepository.save(income);
        } else {
            throw new ResourceNotFoundException("Income not found with id: " + income_id);
        }
    }

    /**
     * Deletes an existing income object for the authenticated user
     * 
     * @param income_id      the id of the Income object to be deleted
     * @param authentication the authentication object representing the current user
     * @return the deleted Income object
     * @throws ResourceNotFoundException if the Income object with the given id is
     *                                   not found
     */
    @Override
    public Income deleteIncome(int income_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Income oldIncome = iRepository.findByIncome_Id(income_id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + income_id));
        if (oldIncome.getUser_id() == user.getId()) {
            iRepository.delete(oldIncome);
            return oldIncome;
        } else {
            throw new ResourceNotFoundException("Income not found with id: " + income_id);
        }
    }

    /**
     * Gets all Income for the authenticated user for a given month and year
     * 
     * @param monthyear      the month and year to get Income for
     * @param authentication the authentication of the user
     * @return list of all income for the authenticated user for a given month
     *         and year
     */
    @Override
    public List<Income> getIncomeMonthYear(MonthYearRequest monthyear, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String start = monthyear.getYear() + "-" + monthyear.getMonth() + "-01";
        String end = LocalDate.of(monthyear.getYear(), monthyear.getMonth(), 1).plusMonths(1).toString();

        // TODO FIX THIS
        // String end = monthyear.getYear() + "-" + NewMonth + "-01";

        return iRepository.findAllByUserIdAndDateBetween(user.getId(), start, end);

    }

    /**
     * Gets all incomes for the authenticated user for a given date
     * 
     * @param date           the date to get incomes for
     * @param authentication the authentication of the user
     * @return list of all incomes for the authenticated user for a given date
     *         (yyyy-mm-dd)
     */
    @Override
    public List<Income> getIncomeDate(DateRequest date, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return iRepository.findAllByUserIdAndDate(user.getId(), date.getDate());
    }

    /**
     * Gets the total Incomes for the authenticated user
     * 
     * @param authentication the authentication of the user
     * @return the total incomes for the authenticated user
     * 
     */
    @Override
    public int getTotalIncome(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Income> incomeList = iRepository.findAllByUserId(user.getId());
        int totalIncome = 0;
        for (Income income : incomeList) {
            totalIncome += income.getSum();
        }
        return totalIncome;
    }

}
