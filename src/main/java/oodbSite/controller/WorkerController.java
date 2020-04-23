package oodbSite.controller;

import oodbSite.domain.Bank;
import oodbSite.domain.Worker;
import oodbSite.repository.BankRepository;
import oodbSite.repository.WorkerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("/workers")
public class WorkerController {
    private WorkerRepository workerRepository;
    private BankRepository bankRepository;

    public WorkerController(WorkerRepository workerRepository, BankRepository bankRepository) {
        this.workerRepository = workerRepository;
        this.bankRepository = bankRepository;
    }

    @GetMapping
    public String workers(@RequestParam(required = false, defaultValue = "") String bankName, Model model) {
        model.addAttribute("banks", bankRepository.findAll());
        if (!bankName.isEmpty()) {
            model.addAttribute("bankName", bankName);
            model.addAttribute("workers", workerRepository.findByBank(bankName));
        } else {
            model.addAttribute("workers", new ArrayList<>());
        }
        return "workers";
    }

    @PostMapping
    public String addWorker(@RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String email,
                            @RequestParam String phoneNumber,
                            @RequestParam String code,
                            @RequestParam String position,
                            @RequestParam String bankName
    ) {
        Worker worker = new Worker();
        worker.setBank(bankRepository.findByName(bankName));
        worker.setEmail(email);
        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setPhoneNumber(phoneNumber);
        worker.setCode(code);
        worker.setPosition(position);

        workerRepository.save(worker);
        return "redirect:/workers";
    }
}
