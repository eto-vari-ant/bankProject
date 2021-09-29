package com.itstep.project.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "operations_with_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationsWithCards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
