package ru.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.example.dto.client.ClientDTO;
import ru.example.service.ClientService;
import ru.example.validation.validator.ClientDTOValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientDTOValidator clientDTOValidator;

    @GetMapping
    public String findAllClients(Model model) {
        model.addAttribute("clients", clientService.findAllClients());
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findClientById(@PathVariable("id") int id, Model model) {
        return clientService.findClientById(id)
                .map(clientDTO -> {
                    model.addAttribute("client", clientDTO);
                    return "client/client";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") ClientDTO clientDTO) {
        return "client/newClient";
    }

    @PostMapping
    public String create(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                         BindingResult bindingResult) {
        clientDTOValidator.validate(clientDTO, bindingResult);
        return bindingResult.hasErrors()
                ? "client/newClient"
                : "redirect:/api/clients/" + clientService.saveClient(clientDTO).getId();
    }

    @GetMapping("/{id}/edit")
    public String editClient(@PathVariable("id") int id, Model model) {
        return clientService.findClientById(id)
                .map(clientDTO -> {
                    model.addAttribute("client", clientDTO);
                    return "client/editClient";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public String updateClient(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                               BindingResult bindingResult) {
        clientDTOValidator.validate(clientDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "client/editClient";
        }

        clientService.updateClient(clientDTO);
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