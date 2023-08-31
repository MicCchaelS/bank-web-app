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
    public String findAllClients(Model model) {
        model.addAttribute("clients", clientService.findAllClients());
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findClientById(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.findClientById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "client/client";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "client/newClient";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Client client) {
        return "redirect:/api/clients/" + clientService.saveClient(client);
    }

    @GetMapping("/{id}/edit")
    public String editClient(@PathVariable("id") int id, Model model) {
        model.addAttribute("client", clientService.findClientById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "client/editClient";
    }

    @PatchMapping("/{id}")
    public String updateClient(Client client) {
        clientService.updateClient(client);
        return "redirect:/api/clients/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable("id") int id) {
        if (!clientService.deleteClient(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/clients";
    }
}