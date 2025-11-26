package no.idatt1002.group6.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expences")
public class Expences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expence_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "sum")
    private int sum;

    @Column(name = "reccuring")
    private boolean recurring;

    @Column(name = "date")
    private String date;

    @Column(name = "user_id")
    private int user_id;

}
