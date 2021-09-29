package com.itstep.project.bank.service;

import com.itstep.project.bank.model.BankAccount;
import com.itstep.project.bank.model.Card;
import com.itstep.project.bank.model.Clients;
import com.itstep.project.bank.model.Roles;
import com.itstep.project.bank.repository.BankAccountRepository;
import com.itstep.project.bank.repository.CardsRepository;
import com.itstep.project.bank.repository.ClientsRepository;
import com.itstep.project.bank.repository.RolesRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class ClientsServiceImpl implements ClientsService, UserDetailsService {
    @Autowired
    CardsRepository cardsRepository;

    @Autowired
    CardsService cardsService;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Clients findClientsById(int id) {
        if (clientsRepository.findClientsById(id) != null) {
            return clientsRepository.findClientsById(id);
        } else {
            return new Clients();
        }
    }

    public boolean isAnyHaveThisEmail(Clients client){
        Clients clientFromDB = clientsRepository.findClientsByEmail(client.getEmail());
        if (clientFromDB != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean save(Clients client) {
        Clients clientFromDB = clientsRepository.findClientsByPassportInfo(client.getPassportInfo());
        if (clientFromDB != null) {
            return false;
        }
        client.setBankAccount(bankAccountService.createBankAccount());
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        client.setRoles(rolesRepository.findRolesByName("ROLE_CLIENT"));
        clientsRepository.save(client);
        return true;
    }

    @Override
    public List<Clients> findAllClients() {
        return (List<Clients>) clientsRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        if (clientsRepository.findClientsById(id) != null) {
            int baId = clientsRepository.findClientsById(id).getBankAccount().getId();
            BankAccount bankAccount = bankAccountRepository.findById(baId);
            List <Card> cards = bankAccount.getCards();
            for(Card card:cards){
                cardsRepository.deleteById(card.getId());
            }
            clientsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkClient(Clients client) {
        String email = client.getEmail();
        Clients clientInList = clientsRepository.findClientsByName(email);
        if (client == null) {
            return false;
        }
        if (clientInList.getPassword().equals(client.getPassword())) {
            return true;
        }
        return false;

    }

    @Override
    public Clients findClientsByEmail(String email) {
        return clientsRepository.findClientsByEmail(email);
    }

    @Override
    public boolean activateClient(int id) {
        Clients client = clientsRepository.findClientsById(id);
        if (client != null) {
            client.setIsActive(1);
            return true;
        }
        return false;
    }

    @Override
    public boolean makeClientAdmin(int id) {
        Clients client = clientsRepository.findClientsById(id);
        if (client != null) {
            Set<Roles> roles = client.getRoles();
            roles.add(rolesRepository.findByName("ROLE_ADMIN"));
            client.setRoles(roles);
            return true;
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Clients client = clientsRepository.findClientsByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException("Client is not found");
        }
        if (client.getIsActive() == 0) {
            throw new UsernameNotFoundException("This account is not activated.");
        }
        UserDetails clientDet = User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .authorities("ROLE_CLIENT", "ROLE_ADMIN")
                .build();
        return clientDet;
    }
}
