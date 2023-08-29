package ru.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clients/{clientId}/accounts")
public class AccountController {

    @GetMapping
    public String findAll() {

        return "account/accounts";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {

        return "account/account";
    }

    @GetMapping("/new")
    public String newClient() {

        return "account/newAccount";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model) {

        return "redirect:/api/clients/{clientId}/accounts"; //+ "id";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        return "redirect:/api/clients";
    }

//    @PostMapping("/top-up")
//    public String topUp() {
//
//        return "redirect:/api/clients/{clientId}/accounts"; //+ "id";
//    }
//
//    @PostMapping("/withdraw")
//    public String withdraw() {
//
//        return "redirect:/api/clients/{clientId}/accounts"; //+ "id";
//    }
}