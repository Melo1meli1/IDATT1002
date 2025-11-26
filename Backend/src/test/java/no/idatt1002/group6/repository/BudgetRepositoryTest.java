package no.idatt1002.group6.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import no.idatt1002.group6.model.Budget;

@DataJpaTest
public class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository expenceRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findAllByUserId() {
        Budget budget = new Budget();
        budget.setUser_id(1);
        testEntityManager.persist(budget);
        testEntityManager.flush();

        List<Budget> budgets = expenceRepository.findAllByUserId(1);

        assertTrue(budgets.size() > 0);

    }
}
