package org.example.Controllers;

import org.example.Entities.Bank;
import org.example.Entities.CreditOffer;
import org.example.service.BankService;
import org.example.service.ClientService;
import org.example.service.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditOfferService creditOfferService;


    // Get all banks
    @GetMapping("/banks")
    public String getAllBanks(Model model) {
        model.addAttribute("banks", bankService.getAllBanks());
        return "banks"; // Thymeleaf template for bank list
    }


    // Get a specific bank by ID
    @GetMapping("/{id}")
    public String getBankById(@PathVariable Long id, Model model) {
        Bank bank = bankService.getBankById(id);

        List<CreditOffer> creditOffersForAllClients = bank.getClients().stream()
                .flatMap(client -> creditOfferService.getCreditOffersByClientId(client.getId()).stream())
                .collect(Collectors.toList());
        model.addAttribute("creditOffersForAllClients", creditOffersForAllClients);


        if (bank != null) {
            model.addAttribute("bank", bank);
            return "bank-info"; // Thymeleaf template for bank details
        } else {
            // Handle bank not found
            return "error"; // Thymeleaf template for error
        }
    }

    // Create a new bank
    @PostMapping("/create")
    public String createBank(Bank bank) {
        bankService.saveBank(bank);
        return "redirect:/bank/banks"; // Redirect to bank list
    }

    // Delete a bank by ID
    @PostMapping("/delete/{id}")
    public String deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return "redirect:/bank/banks"; // Redirect to bank list
    }

    // Display the form to create a new bank
    @GetMapping("/create")
    public String createBankForm(Model model) {
        model.addAttribute("bank", new Bank());
        return "createBankForm";
    }
}