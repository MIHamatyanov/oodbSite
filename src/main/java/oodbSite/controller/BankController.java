package oodbSite.controller;

import oodbSite.domain.Bank;
import oodbSite.repository.BankRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/banks")
public class BankController {
    private BankRepository bankRepository;

    public BankController(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @GetMapping
    public String banks(Model model) {
        model.addAttribute("banks", bankRepository.findAll());
        return "banks";
    }

    @PostMapping
    public String saveBank(@RequestParam String name) {
        Bank bank = new Bank();
        bank.setName(name);
        bankRepository.save(bank);
        return "redirect:/banks";
    }
}
