package com.itstep.project.bank.repository;

import com.itstep.project.bank.model.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientsRepository extends JpaRepository<Clients, Integer> {
    Clients findClientsById(int id);

    Clients findClientsByName(String name);

    Clients findClientsByPassportInfo(String passportInfo);

    Clients findClientsByEmail(String email);

}
