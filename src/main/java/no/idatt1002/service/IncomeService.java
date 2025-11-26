package no.idatt1002.service;

import java.util.List;

import no.idatt1002.AppState;
import no.idatt1002.Model.Income;
import no.idatt1002.repository.Cache;
import no.idatt1002.repository.IncomeRepository;

/**
 * Service class for managing income objects
 */
public class IncomeService {

    private AppState appState;

    private IncomeRepository iRepository;

    public IncomeService() {
        appState = AppState.getInstance();
        iRepository = new IncomeRepository();
    }

    /**
     * This method retrieves a list of income objects and caches them
     * 
     * @return A List of income objects
     */
    public List<Income> incomeList() {
        Cache cache = appState.getCache();

        List<Income> cachedIncomeData = cache.getCachedData("income");

        if (cachedIncomeData == null) {
            List<Income> incomeData = iRepository.getIncome();
            cache.setCachedData("income", incomeData);
            return incomeData;
        }
        return cachedIncomeData;

    }

    /**
     * This method retrieves a list of income objects and caches them
     * 
     * @return A List of income objects
     */

    public List<Income> getIncomeDataFromCache() {
        Cache cache = appState.getCache();

        List<Income> list = cache.getCachedData("income");
        if (cache.getCachedData("income") == null) {
            List<Income> incomeList = iRepository.getIncome();
            cache.setCachedData("income", list);

            return incomeList;
        }
        return list;
    }

    /**
     * This method adds an income object to the database and updates the cache
     * 
     * @param expense The income object to add
     */
    public void addIncome(Income income) {

        iRepository.addIncome(income);

        List<Income> cachedIncomeList = appState.getCache().getCachedData("income");
        cachedIncomeList.add(income);
        appState.getCache().setCachedData("income", cachedIncomeList);

    }

    /**
     * This method updates an existing income object in the database and updates
     * the cache
     * 
     * @param expense The income object to update
     */
    public void updateIncome(Income income) {
        iRepository.updateIncome(income);

        List<Income> incomeList = getIncomeDataFromCache();
        for (int i = 0; i < incomeList.size(); i++) {
            if (incomeList.get(i).getId() == income.getId()) {
                incomeList.set(i, income);
            }
        }

        Cache cache = appState.getCache();
        cache.setCachedData("income", incomeList);

    }

}
