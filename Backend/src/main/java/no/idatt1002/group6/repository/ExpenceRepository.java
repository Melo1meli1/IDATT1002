package no.idatt1002.group6.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import no.idatt1002.group6.model.Expences;

@Repository
public interface ExpenceRepository extends JpaRepository<Expences, Integer> {

    @Query("select i from Expences i where i.user_id = :id")
    public List<Expences> findAllByUserId(@Param("id") int id);

    @Query("select i from Expences i where i.id = :income_id")
    Optional<Expences> findByExpence_Id(@Param("income_id") int income_id);

    @Query("select i from Expences i where i.user_id = :id and i.date between :start and :end")
    public List<Expences> findAllByUserIdAndDateBetween(@Param("id") int id, @Param("start") String start,
            @Param("end") String end);

    @Query("select i from Expences i where i.user_id = :id and i.date = :date")
    public List<Expences> findAllByUserIdAndDate(@Param("id") int id, @Param("date") String date);

    @Query("select i from Expences i where i.user_id = :id and i.date between :start and :end")
    public List<Expences> findIncomeByUserIdForCurrentMonth(@Param("id") int id, @Param("start") String start,
            @Param("end") String end);

}
