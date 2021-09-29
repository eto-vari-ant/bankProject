package com.itstep.project.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Size(min = 2, max = 20, message = "Name is too short or long.")
    private String name;

    @Size(min = 2, max = 32 , message = "Surname is too short or long.")
    private String surname;

    @Email( message = "Please enter your email.")
    private String email;

    @Size(min = 5, message = "Please make your password reliable.")
    private String password;

    @Transient
    private String passwordConfirm;


    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Size(min = 14, max = 14, message = "PIN consists of 14 symbols.")
    @Column(name = "passport_info", nullable = false)
    private String passportInfo;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @ManyToMany
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    private BankAccount bankAccount;




}
