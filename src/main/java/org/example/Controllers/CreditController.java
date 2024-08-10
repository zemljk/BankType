package org.example.Controllers;

import org.example.Entities.Credit;
import org.example.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    // Get all credits
    @GetMapping("/credits")
    public String getAllCredits(Model model) {
        model.addAttribute("credits", creditService.getAllCredits());
        model.addAttribute("credit", new Credit()); // Create a new Credit object
        return "credits";
    }

    // Get a specific credit by ID
    @GetMapping("/{id}")
    public String getCreditById(@PathVariable Long id, Model model) {
        Credit credit = creditService.getCreditById(id);
        if (credit != null) {
            model.addAttribute("credit", credit);
            return "credit-info"; // Name of your Thymeleaf template for credit details
        } else {
            // Handle credit not found
            return "error"; // Name of your Thymeleaf template for error
        }
    }

    // Create a new credit
    @PostMapping("/create")
    public String createCredit(@ModelAttribute Credit credit) {
        creditService.saveCredit(credit);
        return "redirect:/credit/credits"; // Redirect to credit list (with correct path)
    }


    @GetMapping("/create")
    public String createCreditForm(Model model) {
        model.addAttribute("credit", new Credit());
        return "createCredit";
    }

    // Delete a credit by ID
    @PostMapping("/delete/{id}")
    public String deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return "redirect:/credit/credits"; // Redirect to credit list (with correct path)
    }
}