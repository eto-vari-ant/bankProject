package com.itstep.project.bank.controller;

import com.itstep.project.bank.service.ClientsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private ClientsServiceImpl clientsService;

    @GetMapping("/admin")
    public String clientList(Model model) {
        model.addAttribute("clients", clientsService.findAllClients());
        return "admin";
    }

    @PostMapping("/admin/activate/{id}")
    public String activate(@PathVariable("id") int id, Model model) {
       clientsService.activateClient(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/manage/{id}")
    public String manage(@PathVariable("id") int id, Model model) {
        clientsService.makeClientAdmin(id);
        return "redirect:/admin";
    }
    @PostMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        clientsService.deleteById(id);
        return "redirect:/admin";
    }

    


}
