package com.itstep.project.bank.service;

import com.itstep.project.bank.model.Card;
import com.itstep.project.bank.model.Clients;

import java.text.ParseException;
import java.util.List;

public interface CardsService {

    boolean disactivateCard(int id);
    boolean deleteCard(int id);
    Card newCard(Clients client) throws ParseException;
}
