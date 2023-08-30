package ru.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.example.model.Client;
import ru.example.service.ClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "client/client";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "client/newClient";
    }

    @PostMapping
    public String create(Client client) {
        return "redirect:/api/clients/" + clientService.create(client);
    }

    @GetMapping("/{id}/edit")
    public String editClient(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "client/editClient";
    }

    @PatchMapping("/{id}")
    public String update(Client client) {
        clientService.update(client);
        return "redirect:/api/clients/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        if (!clientService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/clients";
    }
}