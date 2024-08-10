package org.example.service;


import org.example.Entities.Client;
import org.example.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Get all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a client by its ID
    public Client getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

    // Save a new client
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Delete a client
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}

