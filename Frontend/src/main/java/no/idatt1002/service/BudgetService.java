package no.idatt1002.service;

import java.util.ArrayList;

import no.idatt1002.AppState;
import no.idatt1002.Model.Budget;
import no.idatt1002.Model.Priority;
import no.idatt1002.Model.PriorityCategory;
import no.idatt1002.Model.Priority.PriorityEnum;
import no.idatt1002.Model.PriorityCategory.PriorityCategoryEnum;
import no.idatt1002.repository.BudgetRepository;
import no.idatt1002.repository.Cache;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Service class for Budget
 */
public class BudgetService {
    private BudgetRepository bRepository;

    private AppState appState;

    public BudgetService() {
        appState = AppState.getInstance();
        bRepository = new BudgetRepository();
    }

    /**
     * Gets the budget data from the cache, if it is not there it will get it from
     * the database and put it in the cache
     * 
     * @return ObservableList<Budget> budgetData from cache
     */
    public ObservableList<Budget> getBudgetDataFromCache() {
        Cache cache = appState.getCache();
        ObservableList<Budget> cachedBudgetData = cache.getObservableListCachedData("budget");

        if (cachedBudgetData == null) {
            List<Budget> budgetData = bRepository.getBudget();

            ObservableList<Budget> budgetDataObservable = FXCollections.observableArrayList(budgetData);

            cache.setCachedData("budget", budgetDataObservable);
            return budgetDataObservable;

        }
        return cachedBudgetData;
    }

    /**
     * Adds the budget to the database and updates the cahce with it
     * 
     * @param b budget to be added
     */
    public void addBudget(Budget b) {
        bRepository.addBudget(b);
        ObservableList<Budget> cachedBudgetList = appState.getCache().getObservableListCachedData("budget");
        cachedBudgetList.add(b);
        appState.getCache().setCachedData("budget", cachedBudgetList);

    }

    /**
     * Updates a budget in the database and updates the cache with it
     * 
     * @param b budget to be updated
     */
    public void updateBudget(Budget b) {

        bRepository.updateBudget(b);

        List<Budget> cachedBudgetList = appState.getCache().getCachedData("budget");
        for (Budget c : cachedBudgetList) {
            if (c.getId() == b.getId()) {
                c.setCategory(b.getCategory());
                c.setSum(b.getSum());
                c.setPriority(b.getPriority());
                c.setIe(b.getIe());
            }
        }
        appState.getCache().setCachedData("budget", cachedBudgetList);

    }

    /**
     * gets the priority data from the cache, if it is not there it will get it
     * from the database and put it in the cache
     * 
     * 
     */
    public List<Priority> getPriorityFromCache() {
        Cache cache = appState.getCache();
        List<Priority> cachedPriorityData = cache.getCachedData("priority");

        if (cachedPriorityData == null) {
            List<Priority> priorityData = getPriority();

            cache.setCachedData("priority", priorityData);
            return priorityData;

        }
        return cachedPriorityData;
    }

    /**
     * Gets the priority data from the getBudgetDataFromCache () method
     * and calculates the sum of each priority
     * 
     * 
     */
    public List<Priority> getPriority() {
        List<Budget> budgetList = getBudgetDataFromCache();

        List<Priority> priorityList = new ArrayList<Priority>();

        Priority p1 = new Priority(PriorityEnum.NECESSARY, 0);
        Priority p2 = new Priority(PriorityEnum.SAVING, 0);
        Priority p3 = new Priority(PriorityEnum.OK, 0);
        Priority p4 = new Priority(PriorityEnum.UNNECESSARY, 0);
        for (Budget b : budgetList) {

            if (b.getPriority() == 0) {
                p1.setSum(p1.getSum() + b.getSum());
            } else if (b.getPriority() == 1) {
                p2.setSum(p2.getSum() + b.getSum());
            } else if (b.getPriority() == 2) {
                p3.setSum(p3.getSum() + b.getSum());
            } else if (b.getPriority() == 3) {
                p4.setSum(p4.getSum() + b.getSum());
            }

        }
        priorityList.add(p1);
        priorityList.add(p2);
        priorityList.add(p3);
        priorityList.add(p4);

        return priorityList;
    }

    /**
     * Get the priority categories from the getBudgetDataFromCache () method
     * and calculates what categories is what priority
     * 
     * @return
     */
    public List<PriorityCategory> getPriorityCategories() {

        List<PriorityCategory> priorityCategories = new ArrayList<PriorityCategory>();
        List<Budget> budgetList = getBudgetDataFromCache();

        for (Budget b : budgetList) {
            if (b.getPriority() == 0) {
                PriorityCategory p = new PriorityCategory(PriorityCategoryEnum.NECESSARY, b.getCategory());
                priorityCategories.add(p);
            } else if (b.getPriority() == 1) {
                PriorityCategory p = new PriorityCategory(PriorityCategoryEnum.OK, b.getCategory());
                priorityCategories.add(p);
            } else if (b.getPriority() == 2) {
                PriorityCategory p = new PriorityCategory(PriorityCategoryEnum.UNNECESSARY, b.getCategory());
                priorityCategories.add(p);
            } else if (b.getPriority() == 3) {
                PriorityCategory p = new PriorityCategory(PriorityCategoryEnum.SAVING, b.getCategory());
                priorityCategories.add(p);
            }

        }
        return priorityCategories;

    }

    /**
     * Gets the priority category data from the cache, if it is not there it will
     * get it
     * from the database and put it in the cache
     * 
     * @return
     */
    public List<PriorityCategory> getPriorityCategoryFromCache() {
        Cache cache = appState.getCache();
        List<PriorityCategory> cachedPriorityCategoryData = cache.getCachedData("priorityCategory");

        if (cachedPriorityCategoryData == null) {
            List<PriorityCategory> priorityCategoryData = getPriorityCategories();

            cache.setCachedData("priorityCategory", priorityCategoryData);
            return priorityCategoryData;

        }
        return cachedPriorityCategoryData;
    }

}
