package com.itstep.project.bank.repository;

import com.itstep.project.bank.model.BankAccount;
import com.itstep.project.bank.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    BankAccount findById(int id);
}
