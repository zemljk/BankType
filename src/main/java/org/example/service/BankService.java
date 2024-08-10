package org.example.service;

import org.example.Entities.Bank;
import org.example.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BankService {

    private final BankRepository bankRepository;


    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank getBankById(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        return bank.orElse(null);
    }

    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }


}