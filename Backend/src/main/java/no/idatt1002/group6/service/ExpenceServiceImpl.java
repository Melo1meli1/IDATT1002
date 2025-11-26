package no.idatt1002.group6.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import no.idatt1002.group6.config.DateRequest;
import no.idatt1002.group6.config.MonthYearRequest;
import no.idatt1002.group6.exeptions.ResourceNotFoundException;
import no.idatt1002.group6.model.Expences;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.ExpenceRepository;

/**
 * Service class for handling Expence-related operations
 */

@Service
public class ExpenceServiceImpl implements ExpenceService {

    @Autowired
    private ExpenceRepository eRepository;

    /**
     * Gets all expences for the authenticated user
     *
     * @param authentication the authentication of the user
     * @return list of all expences for the authenticated user
     */
    @Override
    public List<Expences> getExpence(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return eRepository.findAllByUserId(user.getId());
    }

    /**
     * Creates an expence for the authenticated user
     *
     * @param expence        the expence to be created
     * @param authentication the authentication of the user
     * @return the created expence
     */
    @Override
    public Expences createExpence(Expences expence, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        expence.setUser_id(user.getId());

        String category = expence.getCategory();
        category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
        expence.setCategory(category);

        return eRepository.save(expence);
    }

    /**
     * Updates an existing expence for the authenticated user
     *
     * @param expence_id     the id of the expence to be updated
     * @param expence        the updated expence
     * @param authentication the authentication of the user
     * @return the updated expence
     */
    @Override
    public Expences updateExpence(int expence_id, Expences expence, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Expences oldExpence = eRepository.findByExpence_Id(expence_id)
                .orElseThrow(() -> new ResourceNotFoundException("Expence not found with id: " + expence_id));
        if (oldExpence.getUser_id() == user.getId()) {
            expence.setId(expence_id);
            expence.setUser_id(user.getId());

            String category = expence.getCategory();
            category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
            expence.setCategory(category);

            // Should this be save?
            return eRepository.save(expence);
        } else {
            throw new ResourceNotFoundException("Expence not found with id: " + expence_id);
        }
    }

    /**
     * Deletes an existing expence for the authenticated user
     *
     * @param expence_id     the id of the expence to be deleted
     * @param authentication the authentication of the user
     * @return the deleted expence
     */
    @Override
    public Expences deleteExpence(int expence_id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Expences oldExpence = eRepository.findByExpence_Id(expence_id)
                .orElseThrow(() -> new ResourceNotFoundException("Expence not found with id: " + expence_id));
        if (oldExpence.getUser_id() == user.getId()) {
            eRepository.delete(oldExpence);
            return oldExpence;
        } else {
            throw new ResourceNotFoundException("Expence not found with id: " + expence_id);
        }

    }

    /**
     * Gets all expences for the authenticated user for a given month and year
     * 
     * @param monthyear      the month and year to get expences for
     * @param authentication the authentication of the user
     * @return list of all expences for the authenticated user for a given month
     *         and year
     */
    @Override
    public List<Expences> getExpenceMonthYear(MonthYearRequest monthyear, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String start = monthyear.getYear() + "-" + monthyear.getMonth() + "-01";
        int NewMonth = monthyear.getMonth() + 1;
        String end = monthyear.getYear() + "-" + NewMonth + "-01";

        return eRepository.findAllByUserIdAndDateBetween(user.getId(), start, end);

    }

    /**
     * Gets all expences for the authenticated user for a given date
     * 
     * @param date           the date to get expences for
     * @param authentication the authentication of the user
     * @return list of all expences for the authenticated user for a given date
     *         (yyyy-mm-dd)
     */
    @Override
    public List<Expences> getExpenceDate(DateRequest date, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return eRepository.findAllByUserIdAndDate(user.getId(), date.getDate());
    }

    /**
     * Gets the total expences for the authenticated user
     * 
     * @param authentication the authentication of the user
     * @return the total expences for the authenticated user
     * 
     */
    @Override
    public int getTotalExpence(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Expences> expencesList = eRepository.findAllByUserId(user.getId());
        int totalIncome = 0;
        for (Expences expences : expencesList) {
            totalIncome += expences.getSum();
        }
        return totalIncome;
    }

    /**
     * Gets the current months expences for the authenticated user
     * 
     * @param monthyear      the month and year to get expences for
     * @param authentication the authentication of the user
     * @return the the current months expences
     * 
     */
    @Override
    public List<Expences> getCurrentExpence(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        LocalDate date = LocalDate.now();
        LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), 1);

        return eRepository.findIncomeByUserIdForCurrentMonth(user.getId(), startDate.toString(), date.toString());

    }

}
