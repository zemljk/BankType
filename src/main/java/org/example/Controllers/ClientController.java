package org.example.Controllers;


import org.example.Entities.Bank;
import org.example.Entities.Client;
import org.example.service.BankService;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BankService bankService;

    // Get all clients
    @GetMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("banks", bankService.getAllBanks()); // Add banks to the model
        return "clients";
    }

    // Get a specific client by ID
    @GetMapping("/{id}")
    public String getClientById(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            model.addAttribute("client", client);
            return "client-info"; // Name of your Thymeleaf template for client details
        } else {
            return "error";
        }
    }

    // Create a new client
    @PostMapping("/create")
    public String createClient(@ModelAttribute Client client) {
        Bank bank = bankService.getBankById(client.getBank().getId()); // Get bank ID from client object
        client.setBank(bank);
        clientService.saveClient(client);
        return "redirect:/client/clients";
    }

    // Delete a client by ID
    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/client/clients"; // Redirect to client list
    }

    // Display the form to create a new client
    @GetMapping("/create")
    public String createClientForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("banks", bankService.getAllBanks());
        return "createClientForm";
    }
}