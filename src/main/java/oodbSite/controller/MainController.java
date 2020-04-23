package oodbSite.controller;

import oodbSite.domain.Bank;
import oodbSite.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private BankRepository repository;

    @GetMapping("/")
    public String main() {
        return "main";
    }
}
