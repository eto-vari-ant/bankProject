package com.itstep.project.bank.controller;

import com.itstep.project.bank.model.BankAccount;
import com.itstep.project.bank.model.Card;
import com.itstep.project.bank.model.Clients;
import com.itstep.project.bank.repository.BankAccountRepository;
import com.itstep.project.bank.repository.CardsRepository;
import com.itstep.project.bank.repository.ClientsRepository;
import com.itstep.project.bank.service.BankAccountService;
import com.itstep.project.bank.service.CardsService;
import com.itstep.project.bank.service.ClientsService;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
public class BankController {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    CardsRepository cardsRepository;

    @Autowired
    CardsService cardsService;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    ClientsService clientsService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Please check your login and password or wait before the authentication.");
        }

        return "login";
    }

        @GetMapping("/welcome")
    public String welcomePage() {
        return "welcome";
    }

    @GetMapping("/home")
    public String homePage(Principal principal, Model model) {
        Clients clients = clientsRepository.findClientsByEmail(principal.getName());
        model.addAttribute("client", clients);
        return "home";
    }




//    @PostMapping("/home/renameba/{id}/{name}")
//    public String delete( @PathVariable("id") int id,@PathVariable("name") String name, Model model) {
//        model.addAttribute("renameform");
//        bankAccountService.renameBA(id,name);
//        return "redirect:/home";
//    }


    @GetMapping("/cards")
    public String cards(Principal principal, Model model){
        Clients clients = clientsRepository.findClientsByEmail(principal.getName());
        List<Card> cards = clients.getBankAccount().getCards();
        model.addAttribute("cards", cards);
        return "cards";
    }

    @PostMapping("/cards/disactivate/{id}")
    public String disactivate(@PathVariable("id") int id, Model model) {
        cardsService.disactivateCard(id);
        return "redirect:/cards";
    }

    @PostMapping("/cards/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        cardsService.deleteCard(id);
        return "redirect:/cards";
    }

    @GetMapping("/cards/edit/{id}")
    public String info(@PathVariable("id") int id, Model model) {
        Card card = cardsRepository.findCardById(id);
        model.addAttribute("card", card);
        return "cardInfo";
    }

    @PostMapping("/cards/update/{id}")
    public String infopost(@PathVariable("id") int id, @ModelAttribute("card") @Valid Card card, BindingResult bindingResult, Model model) {
        Card cardInDB = cardsRepository.findCardById(id);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "cardInfo";

        }
        cardInDB.setName(card.getName());
        cardsRepository.save(cardInDB);
        return "redirect:/cards";
    }

    @GetMapping("/cards/add")
    public String addCard(Principal principal,Model model) throws ParseException {
        Clients client = clientsRepository.findClientsByEmail(principal.getName());
        Card card = cardsService.newCard(client);
        model.addAttribute("card",card);
        return "addCard";
    }

    @PostMapping("/cards/add")
    public String addCardPost(Principal principal,@ModelAttribute("card") @Valid Card card, BindingResult bindingResult, Model model){
        Clients client = clientsRepository.findClientsByEmail(principal.getName());
        BankAccount bankAccount = client.getBankAccount();
        card.setBankAccount(bankAccount);
        card.setIsActive(1);
        System.out.println(card.getBankAccount().getId());
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "addCard";
        }
        cardsRepository.save(card);
        return "redirect:/cards";
    }
    @GetMapping("/transfer/card/{id}")
    public String transferToCard(@PathVariable("id") int id, Model model) {
        BankAccount bankAccount = bankAccountRepository.findById(id);
        List<Card> cards = bankAccount.getCards();
        model.addAttribute("card", cards);
        model.addAttribute("bankAccount", bankAccount);
        return "transferToCard";
    }

    @PostMapping("/transfer/card/well/{id}")
    public String transferToCardPost(@PathVariable("id") int id, @ModelAttribute("card") @Valid Card card, BindingResult bindingResult, Model model) {

        return "redirect:/home";
    }
}
