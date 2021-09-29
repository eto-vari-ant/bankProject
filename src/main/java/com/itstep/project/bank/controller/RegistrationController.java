package com.itstep.project.bank.controller;

import com.itstep.project.bank.model.Clients;
import com.itstep.project.bank.service.ClientsServiceImpl;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private ClientsServiceImpl clientsService;


    @GetMapping("/registration")
    public String registration(Model model) {

        model.addAttribute("newClient", new Clients());

        return "registration";
    }

    @PostMapping("/registration")
    public String addClient(@ModelAttribute("newClient") @Valid Clients newClient, BindingResult bindingResult, Model model) {
        // System.out.println(newClient.toString());


        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "registration";

        }

        if (!newClient.getPassword().equals(newClient.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match.");
            return "registration";
        }

        if (!clientsService.isAnyHaveThisEmail(newClient)) {
            model.addAttribute("emailError", "Client with this email is already registered.");
            return "registration";
        }

        if (!clientsService.save(newClient)) {
            model.addAttribute("passInfError", "Client with this PIN is already registered.");
            return "registration";
        }



        return "redirect:/welcome";
    }
}
