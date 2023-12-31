package ru.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsFilterDTO;
import ru.example.dto.passport.PassportDTO;
import ru.example.service.ClientService;
import ru.example.service.PassportService;
import ru.example.validation.validator.ClientDTOValidator;
import ru.example.validation.validator.PassportDTOValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final PassportService passportService;

    private final ClientDTOValidator clientDTOValidator;
    private final PassportDTOValidator passportDTOValidator;

    @GetMapping
    public String findAllClientsPassports(@ModelAttribute("filter") @Valid ClientsFilterDTO clientsFilterDTO,
                                          BindingResult bindingResult,
                                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientService.findAllClientsAndPassports());
            return "client/clients";
        }

        if (clientsFilterDTO.getLastName() == null) {
            model.addAttribute("clients", clientService.findAllClientsAndPassports());
        } else {
            model.addAttribute("clients", clientService.findAllClientsAndPassportsByFilter(clientsFilterDTO));
        }

        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findClientAndPassportByClientId(@PathVariable("id") long id, Model model) {
        model.addAttribute("client", clientService.findClientById(id));
        model.addAttribute("passport", passportService.findPassportByClientId(id));
        return "client/client";
    }

    @GetMapping("/new")
    public String showNewClientPassportPage(@ModelAttribute("client") ClientDTO clientDTO,
                                            @ModelAttribute("passport") PassportDTO passportDTO) {
        return "client/newClient";
    }

    @PostMapping
    public String createClientAndPassport(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                                          BindingResult bindingResultClient,
                                          @ModelAttribute("passport") @Valid PassportDTO passportDTO,
                                          BindingResult bindingResultPassport) {
        clientDTOValidator.validate(clientDTO, bindingResultClient);
        passportDTOValidator.validate(passportDTO, bindingResultPassport);

        if (bindingResultClient.hasErrors() || bindingResultPassport.hasErrors()) {
            return "client/newClient";
        }

        var client = clientService.saveClient(clientDTO);
        passportService.savePassport(passportDTO, client);

        return "redirect:/api/clients/" + client.getId();
    }

    private long passportId;

    @GetMapping("/{id}/edit")
    public String showEditClientPassportPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("client", clientService.findClientById(id));

        PassportDTO passportDTO = passportService.findPassportByClientId(id);
        passportId = passportDTO.getId();
        model.addAttribute("passport", passportDTO);
        return "client/editClient";
    }

    @PatchMapping("/{id}")
    public String updateClientAndPassport(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                                          BindingResult bindingResultClient,
                                          @ModelAttribute("passport") @Valid PassportDTO passportDTO,
                                          BindingResult bindingResultPassport) {
        passportDTO.setId(passportId);
        clientDTOValidator.validate(clientDTO, bindingResultClient);
        passportDTOValidator.validate(passportDTO, bindingResultPassport);

        if (bindingResultClient.hasErrors() || bindingResultPassport.hasErrors()) {
            return "client/editClient";
        }

        passportService.updatePassport(passportDTO);
        clientService.updateClient(clientDTO);
        return "redirect:/api/clients/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteClientAndPassport(@PathVariable("id") long id) {
        clientService.deleteClient(id);
        return "redirect:/api/clients";
    }
}