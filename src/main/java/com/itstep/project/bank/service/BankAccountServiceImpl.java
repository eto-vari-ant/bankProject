package com.itstep.project.bank.service;

import com.itstep.project.bank.model.BankAccount;
import com.itstep.project.bank.model.Card;
import com.itstep.project.bank.repository.BankAccountRepository;
import com.itstep.project.bank.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    CardsRepository cardsRepository;

    @Autowired
    CardsService cardsService;

    @Autowired
    BankAccountRepository bankAccountRepository;


    @Override
    public BankAccount createBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("MyAccount");
        bankAccount.setAmount(1000);
        return bankAccount;
    }

    @Override
    public boolean renameBA(int id, String name) {
        BankAccount bankAccount = bankAccountRepository.findById(id);
        bankAccount.setName(name);
        bankAccountRepository.save(bankAccount);
        return true;
    }




}
