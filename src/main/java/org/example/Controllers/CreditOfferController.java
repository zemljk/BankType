package org.example.Controllers;


import org.example.Entities.Bank;
import org.example.Entities.Client;
import org.example.Entities.CreditOffer;
import org.example.service.BankService;
import org.example.service.ClientService;
import org.example.service.CreditOfferService;
import org.example.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/creditOffer")
public class CreditOfferController {

    @Autowired
    private CreditOfferService creditOfferService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private BankService bankService;

    // Get all credit offers
    @GetMapping("/creditOffers")
    public String getAllCreditOffers(Model model) {
        model.addAttribute("creditOffers", creditOfferService.getAllCreditOffers());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("credits", creditService.getAllCredits());
        model.addAttribute("banks", bankService.getAllBanks()); // Add banks to the model
        return "creditOffers";
    }

    // Delete a credit offer
    @GetMapping("/{id}/delete")
    public String deleteCreditOffer(@PathVariable Long id) {
        creditOfferService.deleteCreditOffer(id);
        return "redirect:/creditOffer/creditOffers"; // Redirect to credit offers list
    }

    // Create a new credit offer
    @PostMapping("/create") // Use the correct path here
    public String createCreditOffer(@ModelAttribute CreditOffer creditOffer) {
        creditOfferService.saveCreditOffer(creditOffer);
        return "redirect:/creditOffer/creditOffers"; // Redirect to credit offers list
    }

    // Display the form to create a new credit offer
    @GetMapping("/create") // Use the correct path here
    public String createCreditOfferForm(Model model) {
        model.addAttribute("creditOffer", new CreditOffer());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("credits", creditService.getAllCredits());
        return "createCreditOfferForm";
    }

    // Display a specific credit offer (optional, but useful for details)
    @GetMapping("/{id}")
    public String getCreditOfferById(@PathVariable Long id, Model model) {
        CreditOffer creditOffer = creditOfferService.getCreditOfferById(id);
        if (creditOffer != null) {
            model.addAttribute("creditOffer", creditOffer);
            return "creditOffer-info";
        } else {
            return "error";
        }
    }
}