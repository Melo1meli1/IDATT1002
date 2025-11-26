package no.idatt1002.group6.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import no.idatt1002.group6.model.Income;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.IncomeRepository;
import no.idatt1002.group6.service.IncomeServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

    @InjectMocks
    private IncomeServiceImpl incomeService;

    @Mock
    private IncomeRepository iRepository;

    @Mock
    private Authentication authentication;

    private User user;
    private List<Income> incomeList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);

        Income income1 = new Income();
        income1.setUser_id(user.getId());
        income1.setSum(100);

        Income income2 = new Income();
        income2.setUser_id(user.getId());
        income2.setSum(200);

        incomeList = Arrays.asList(income1, income2);
    }

    @Test
    public void getIncomeTest() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(iRepository.findAllByUserId(user.getId())).thenReturn(incomeList);

        List<Income> result = incomeService.getIncome(authentication);

        assertEquals(incomeList.size(), result.size());
        assertEquals(incomeList.get(0).getId(), result.get(0).getId());
        assertEquals(incomeList.get(1).getId(), result.get(1).getId());
    }

    @Test
    public void createIncomeTest() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(iRepository.save(any(Income.class))).thenReturn(incomeList.get(0));

        Income income3 = new Income();
        income3.setSum(900);
        income3.setCategory("Food");
        income3.setName("Food");
        income3.setRecurring(false);
        income3.setDate("2023-04-01");
        income3.setSum(1);

        Income createdIncome = incomeService.createIncome(income3, authentication);

        incomeList = Arrays.asList(incomeList.get(0), incomeList.get(1), createdIncome);
        when(iRepository.findAllByUserId(user.getId())).thenReturn(incomeList);

        List<Income> resultList = incomeService.getIncome(authentication);
        assertEquals(incomeList.size(), resultList.size());
        assertEquals(incomeList.get(2).getId(), resultList.get(2).getId());
        
    }

    @Test
    public void getCurrentIncomeTest(){
        when(authentication.getPrincipal()).thenReturn(user);
        when(iRepository.findAllByUserId(user.getId())).thenReturn(incomeList);

        int result = incomeService.getTotalIncome(authentication);
        assertEquals(300, result);


    }
}
