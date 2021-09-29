package com.itstep.project.bank.service;

import com.itstep.project.bank.model.BankAccount;
import com.itstep.project.bank.model.Card;
import com.itstep.project.bank.model.Clients;
import com.itstep.project.bank.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CardsServiceImpl implements CardsService{

    @Autowired
    CardsRepository cardsRepository;

    @Override
    public boolean disactivateCard(int id) {
        Card card = cardsRepository.findCardById(id);
        if(card != null){
            card.setIsActive(0);
            cardsRepository.save(card);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCard(int id) {
        if(cardsRepository.findCardById(id)!=null){
            cardsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Card newCard(Clients client) throws ParseException {
        Card card = new Card();
        card.setNumber(1111111+(int)(Math.random()*99999999));
        card.setCvv(111+(int)(Math.random()*999));
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.YEAR, +3);
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        Date validityPeriod = dateFormat.parse(formattedDate);
        java.sql.Date sqlDate = new java.sql.Date(validityPeriod.getTime());
        card.setValidityPeriod(sqlDate);
        card.setAmount(0);
        card.setIsActive(1);
        card.setName("MyCard");
        return card;
    }


}
