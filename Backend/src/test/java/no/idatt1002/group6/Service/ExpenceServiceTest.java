package no.idatt1002.group6.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import no.idatt1002.group6.model.Expences;
import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.ExpenceRepository;
import no.idatt1002.group6.service.ExpenceServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenceServiceTest {

    @InjectMocks
    private ExpenceServiceImpl expenceservice;

    @Mock
    private ExpenceRepository eRepository;

    @Mock
    private Authentication authentication;

    private User user;
    private List<Expences> expenceList;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);

        Expences epences1 = new Expences();
        epences1.setUser_id(user.getId());
        epences1.setSum(100);

        Expences epences2 = new Expences();
        epences2.setUser_id(user.getId());
        epences2.setSum(200);

        expenceList = Arrays.asList(epences1, epences2);
    }

    @Test
    public void getExpenceTests() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(eRepository.findAllByUserId(user.getId())).thenReturn(expenceList);

        List<Expences> result = expenceservice.getExpence(authentication);

        assertEquals(expenceList.size(), result.size());
        assertEquals(expenceList.get(0).getId(), result.get(0).getId());
        assertEquals(expenceList.get(1).getId(), result.get(1).getId());
    }

    @Test
    public void createExpenceTest() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(eRepository.save(any(Expences.class))).thenReturn(expenceList.get(0));

        Expences income3 = new Expences();
        income3.setSum(900);
        income3.setCategory("Food");
        income3.setName("Food");
        income3.setRecurring(false);
        income3.setDate("2023-04-01");
        income3.setSum(1);

        Expences createExpence = expenceservice.createExpence(income3, authentication);

        expenceList = Arrays.asList(expenceList.get(0), expenceList.get(1), createExpence);
        when(eRepository.findAllByUserId(user.getId())).thenReturn(expenceList);

        List<Expences> resultList = expenceservice.getExpence(authentication);
        assertEquals(expenceList.size(), resultList.size());
        assertEquals(expenceList.get(2).getId(), resultList.get(2).getId());
        
    }

    @Test
    public void getCurrentIncomeTest(){
        when(authentication.getPrincipal()).thenReturn(user);
        when(eRepository.findAllByUserId(user.getId())).thenReturn(expenceList);

        int result = expenceservice.getTotalExpence(authentication);
        assertEquals(300, result);


    }
}
