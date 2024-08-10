package org.example.service;

import org.example.Entities.Credit;
import org.example.repositories.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    // Get all credits
    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    // Get a credit by its ID
    public Credit getCreditById(Long id) {
        Optional<Credit> credit = creditRepository.findById(id);
        return credit.orElse(null);
    }

    // Save a new credit
    public Credit saveCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    // Delete a credit
    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }
}