package ru.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/clients")
public class ClientController {

    @GetMapping
    public String findAll(Model model) {

        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {

        return "client/client";
    }

    @GetMapping("/new")
    public String newClient() {

        return "client/newClient";
    }

    @PostMapping
    public String create(Model model) {

        return "redirect:/api/client/client/"; //+ "id";
    }

    @GetMapping("/{id}/edit")
    public String editClient(@PathVariable("id") int id, Model model) {

        return "client/editClient";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id) {

        return "redirect:/api/clients/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        return "redirect:/api/clients";
    }
}