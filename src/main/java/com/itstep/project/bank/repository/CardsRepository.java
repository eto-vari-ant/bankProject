package com.itstep.project.bank.repository;

import com.itstep.project.bank.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<Card, Integer> {
    Card findCardById(int id);
}
