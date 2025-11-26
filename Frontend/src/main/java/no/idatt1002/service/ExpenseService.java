package no.idatt1002.service;

import java.util.List;

import no.idatt1002.AppState;
import no.idatt1002.repository.Cache;
import no.idatt1002.repository.ExpenseRepository;
import no.idatt1002.service.config.MonthYearRequest;
import no.idatt1002.Model.Expense;

/**
 * 
 * Service class for managing Expense objects
 */
public class ExpenseService {
    private AppState appState;

    private ExpenseRepository eRepository;

    public ExpenseService() {
        appState = AppState.getInstance();
        eRepository = new ExpenseRepository();
    }

    /**
     * This method retrieves a list of Expense objects and caches them
     * 
     * @return A List of Expense objects
     */
    public List<Expense> expenseList() {
        Cache cache = appState.getCache();

        List<Expense> cachedExpendedData = cache.getCachedData("expence");

        if (cachedExpendedData == null) {
            List<Expense> expenseData = eRepository.getExpense();
            cache.setCachedData("expence", expenseData);
            return expenseData;
        }
        return cachedExpendedData;
    }

    /**
     * This method adds an Expense object to the database and updates the cache
     * 
     * @param expense The Expense object to add
     */
    public void addExpense(Expense expense) {

        eRepository.addExpense(expense);

        List<Expense> cachedExpenseList = appState.getCache().getCachedData("expence");
        cachedExpenseList.add(expense);
        appState.getCache().setCachedData("expence", cachedExpenseList);
    }

    /**
     * This method updates an existing Expense object in the database and updates
     * the cache
     * 
     * @param expense The Expense object to update
     */
    public void updateExpence(Expense expense) {
        System.out.println("Service: Updating expense");

        eRepository.updateExpence(expense);
        System.out.println("yes");

        List<Expense> cachedExpenseList = appState.getCache().getCachedData("expence");
        for (int i = 0; i < cachedExpenseList.size(); i++) {
            if (cachedExpenseList.get(i).getId() == expense.getId()) {
                cachedExpenseList.set(i, expense);
            }
        }
        appState.getCache().setCachedData("expence", cachedExpenseList);

    }

    /**
     * This method retrieves a list of Expense objects for a given month and year
     * from the cache or database
     * 
     * @param request A MonthYearRequest object containing the month and year to
     *                retrieve data for
     * @return A List of Expense objects
     */
    public List<Expense> getExpenceMonthYearFromCache(MonthYearRequest request) {
        String year = Integer.toString(request.getYear());
        String month = Integer.toString(request.getMonth());
        List<Expense> cachedExpenseData = appState.getCache().getCachedData("expense" + month + year);
        if (cachedExpenseData == null) {
            List<Expense> expenseData = getMonthYear(request);
            appState.getCache().setCachedData("expense" + month + year, expenseData);
            return expenseData;
        }
        return cachedExpenseData;
    }

    /**
     * This method retrieves a list of Expense objects for a given month and year
     * from the database
     * 
     * @param request A MonthYearRequest object containing the month and year to
     *                retrieve data for from the database
     * @return A List of Expense objects
     */
    public List<Expense> getMonthYear(MonthYearRequest request) {
        return eRepository.getMonthYear(request);
    }

}
