package no.idatt1002.group6.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import no.idatt1002.group6.model.Income;

@DataJpaTest
public class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findAllByUserIdTest() {
        Income income = new Income();
        income.setUser_id(40);
        testEntityManager.persist(income);

        testEntityManager.flush();
        testEntityManager.clear();

        List<Income> incomeList = incomeRepository.findAllByUserId(40);

        assertTrue(incomeList.size() > 0);
    }

    @Test
    public void findIncomeByUserIdForCurrentMonthTest() {
        Income income = new Income();
        income.setDate("2023-04-01");
        income.setUser_id(40);
        testEntityManager.persist(income);

        testEntityManager.flush();
        testEntityManager.clear();

        List<Income> incomeList = incomeRepository.findIncomeByUserIdForCurrentMonth(40, "2023-04-01", "2023-04-30");

        assertTrue(incomeList.size() == 1);

    }

}
