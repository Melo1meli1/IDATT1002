package no.idatt1002.group6.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import no.idatt1002.group6.model.Expences;

@DataJpaTest
public class ExpenceRepositoryTest {

    @Autowired
    private ExpenceRepository expenceRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findAllByUserIdTest() {
        Expences expence = new Expences();
        expence.setUser_id(40);
        testEntityManager.persist(expence);

        testEntityManager.flush();
        testEntityManager.clear();

        List<Expences> expenceList = expenceRepository.findAllByUserId(40);

        assertTrue(expenceList.size() > 0);
    }

    @Test
    public void findIncomeByUserIdForCurrentMonthTest() {
        Expences income = new Expences();
        income.setDate("2023-04-01");
        income.setUser_id(40);
        testEntityManager.persist(income);

        testEntityManager.flush();
        testEntityManager.clear();

        List<Expences> incomeList = expenceRepository.findIncomeByUserIdForCurrentMonth(40, "2023-04-01", "2023-04-30");

        assertTrue(incomeList.size() == 1);

    }

}
