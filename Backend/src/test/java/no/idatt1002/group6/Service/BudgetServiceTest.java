package no.idatt1002.group6.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import no.idatt1002.group6.model.Budget;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.BudgetRepository;
import no.idatt1002.group6.service.BudgetServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

    @InjectMocks
    private BudgetServiceImpl budgetService;

    @Mock
    private BudgetRepository bRepository;

    @Mock
    private Authentication authentication;

    private User user;
    private List<Budget> budgetList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);

        Budget budget1 = new Budget();
        budget1.setUser_id(user.getId());
        budget1.setSum(100);

        Budget budget2 = new Budget();
        budget2.setUser_id(user.getId());
        budget2.setSum(200);

        budgetList = Arrays.asList(budget1, budget2);
    }

    @Test
    public void getBudgetTest() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(bRepository.findAllByUserId(user.getId())).thenReturn(budgetList);

        List<Budget> result = budgetService.getBudget(authentication);

        assertEquals(budgetList.size(), result.size());
        assertEquals(budgetList.get(0).getId(), result.get(0).getId());
        assertEquals(budgetList.get(1).getId(), result.get(1).getId());
    }

    @Test
    public void createExpenceTest() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(bRepository.save(any(Budget.class))).thenReturn(budgetList.get(0));

         
        Budget budget3 = new Budget();
        budget3.setSum(900);
        budget3.setCategory("Food");

        Budget createBudget = budgetService.createBudget(budget3, authentication);


        budgetList = Arrays.asList(budgetList.get(0), createBudget);
        when(bRepository.findAllByUserId(user.getId())).thenReturn(budgetList);

        List<Budget> resultList = budgetService.getBudget(authentication);
        assertEquals(resultList.size(), resultList.size());
        assertEquals(resultList.get(1).getId(), resultList.get(1).getId());        
    }

}
