package com.itstep.project.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "deposits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 128, message = "Name is too short or long.")
    private String name;
    private int amount;

    @Size(min = 2, max = 256, message = "Description is too short or long.")
    private String description;

    @ManyToMany
    @JoinTable(name = "deposits_fk_operations",
            joinColumns = @JoinColumn(name = "deposit_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id"))
    private Set<OperationsWithDeposits> operationsWithDeposits;

}
