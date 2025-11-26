package no.idatt1002.group6.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import no.idatt1002.group6.model.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    @Query("select i from Budget i where i.user_id = :user_id")
    public List<Budget> findAllByUserId(@Param("user_id") int user_id);

    Optional<Budget> findById(int income_id);

}