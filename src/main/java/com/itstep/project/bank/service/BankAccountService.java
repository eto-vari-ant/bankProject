package com.itstep.project.bank.service;

import com.itstep.project.bank.model.BankAccount;

public interface BankAccountService {
    BankAccount createBankAccount();
    boolean renameBA(int id, String name);
}
