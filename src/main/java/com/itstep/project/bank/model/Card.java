package com.itstep.project.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 32, message = "Name is too short or long.")
    private String name;

    private int number;
    private int cvv;

    @Column(name = "validity_period", nullable = false)
    //@DateTimeFormat(pattern = "dd.MM.YYYY")
    private Date validityPeriod;

    private int amount;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @ManyToMany
    @JoinTable(name = "cards_fk_operations",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id"))
    private Set<OperationsWithCards> operationsWithCards;


    @JoinColumn(name = "ba_id", nullable = false,referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BankAccount bankAccount;

}
