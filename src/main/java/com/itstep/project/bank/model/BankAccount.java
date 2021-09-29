package com.itstep.project.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "bank_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 20, message = "Name is too short.")
    private String name;
    private int amount;

    @OneToOne(mappedBy = "bankAccount")
    private Clients clients;

    @OneToMany(mappedBy = "bankAccount")
    private List<Card> cards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ba_id")
    private List<Deposit> deposits=new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "ba_fk_operations",
            joinColumns = @JoinColumn(name = "ba_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id"))
    private Set<OperationsWithBA> operationsWithBA;
}
