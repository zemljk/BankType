package org.example.repositories;


import org.example.Entities.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
    List<CreditOffer> findCreditOffersByClientId(Long clientId);

}
