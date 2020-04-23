package oodbSite.controller;

import oodbSite.domain.BankAccount;
import oodbSite.domain.Client;
import oodbSite.repository.AccountsRepository;
import oodbSite.repository.BankRepository;
import oodbSite.repository.ClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private ClientRepository clientRepository;
    private BankRepository bankRepository;
    private AccountsRepository accountsRepository;

    public AccountController(ClientRepository clientRepository, BankRepository bankRepository, AccountsRepository accountsRepository) {
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
        this.accountsRepository = accountsRepository;
    }

    @GetMapping
    public String accounts(@RequestParam(required = false, defaultValue = "") String bankName,
                           @RequestParam(required = false, defaultValue = "") String clientId, Model model
    ) {
        model.addAttribute("banks", bankRepository.findAll());
        if (!bankName.isEmpty()) {
            model.addAttribute("bankName", bankName);
            model.addAttribute("clients", clientRepository.findByBank(bankName));
            System.out.println(clientId);
            if (!clientId.isEmpty()) {
                model.addAttribute("clientId", clientId);
                System.out.println(clientId);

                List<BankAccount> bankAccounts = clientRepository.findById(Long.parseLong(clientId)).getBankAccounts();
                System.out.println(bankAccounts.size());
                model.addAttribute("accounts", bankAccounts);
            }
        }
        return "accounts";
    }

    @PostMapping
    public String addWorker(@RequestParam String bankName,
                            @RequestParam String clientId,
                            @RequestParam String accountNumber,
                            @RequestParam String balance
    ) {
        Client client = clientRepository.findById(Long.parseLong(clientId));
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(Long.parseLong(accountNumber));
        bankAccount.setBalance(Long.parseLong(balance));

        accountsRepository.save(bankAccount);
        client.addBankAccount(accountsRepository.getByAccountNumber(bankAccount.getAccountNumber()));
        clientRepository.save(client);
        return "redirect:/clients";
    }
}
