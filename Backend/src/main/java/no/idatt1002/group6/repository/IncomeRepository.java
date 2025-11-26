package no.idatt1002.group6.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import no.idatt1002.group6.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query("select i from Income i where i.user_id = :id")
    public List<Income> findAllByUserId(int id);

    @Query("select i from Income i where i.user_id = :id and i.date between :start and :end")
    public List<Income> findIncomeByUserIdForCurrentMonth(int id, String start, String end);

    @Query("select i from Income i where i.id = :income_id")
    Optional<Income> findByIncome_Id(int income_id);

    @Query("select i from Income i where i.user_id = :id and i.date between :start and :end")
    public List<Income> findAllByUserIdAndDateBetween(int id, String start, String end);

    @Query("select i from Income i where i.user_id = :id and i.date = :date")
    public List<Income> findAllByUserIdAndDate(int id, String date);
}