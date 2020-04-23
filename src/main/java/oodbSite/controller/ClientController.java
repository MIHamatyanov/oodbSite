package oodbSite.controller;

import oodbSite.domain.Client;
import oodbSite.domain.Worker;
import oodbSite.repository.BankRepository;
import oodbSite.repository.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private ClientRepository clientRepository;
    private BankRepository bankRepository;

    public ClientController(ClientRepository clientRepository, BankRepository bankRepository) {
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
    }

    @GetMapping
    public String clients(@RequestParam(required = false, defaultValue = "") String bankName, Model model) {
        model.addAttribute("banks", bankRepository.findAll());
        if (!bankName.isEmpty()) {
            model.addAttribute("bankName", bankName);
            model.addAttribute("clients", clientRepository.findByBank(bankName));
        } else {
            model.addAttribute("clients", new ArrayList<>());
        }
        return "clients";
    }

    @PostMapping
    public String addWorker(@RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String email,
                            @RequestParam String phoneNumber,
                            @RequestParam String bankName
    ) {
        Client client = new Client();
        client.setBank(bankRepository.findByName(bankName));
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);

        clientRepository.save(client);
        return "redirect:/clients";
    }
}
