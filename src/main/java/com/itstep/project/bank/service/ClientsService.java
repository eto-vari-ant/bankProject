package com.itstep.project.bank.service;

import com.itstep.project.bank.model.Clients;

import java.util.List;

public interface ClientsService {
    Clients findClientsById(int id);
    boolean save(Clients client);
    List<Clients> findAllClients();
    boolean deleteById(int id);
    boolean checkClient(Clients client);
    Clients findClientsByEmail(String email);
    boolean activateClient(int id);
    boolean makeClientAdmin(int id);
}
