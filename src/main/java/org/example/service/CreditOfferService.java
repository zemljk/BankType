package org.example.service;

import org.example.Entities.CreditOffer;
import org.example.Entities.PaymentGraphic;
import org.example.repositories.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CreditOfferService {

    @Autowired
    private CreditOfferRepository creditOfferRepository; // Репозиторий для доступа к CreditOffer в базе данных

    // Получение всех кредитных предложений
    public List<CreditOffer> getAllCreditOffers() {
        return creditOfferRepository.findAll();
    }

    // Получение кредитного предложения по ID
    public CreditOffer getCreditOfferById(Long id) {
        Optional<CreditOffer> creditOffer = creditOfferRepository.findById(id);
        return creditOffer.orElse(null); // Возврат null, если кредитное предложение не найдено
    }

    // Сохранение нового кредитного предложения
    public CreditOffer saveCreditOffer(CreditOffer creditOffer) {
        // Calculate the payment schedule
        List<PaymentGraphic> paymentGraphics = calculatePaymentSchedule(creditOffer);

        // Set the calculated payment graphics to the credit offer
        creditOffer.setPaymentGraphics(paymentGraphics);

        // Save the credit offer (which will automatically save the payment graphics)
        return creditOfferRepository.save(creditOffer);
    }

    // Удаление кредитного предложения по ID
    public void deleteCreditOffer(Long id) {
        creditOfferRepository.deleteById(id);
    }


    public List<CreditOffer> getCreditOffersByClientId(Long clientId) {
        return creditOfferRepository.findCreditOffersByClientId(clientId);
    }

    private List<PaymentGraphic> calculatePaymentSchedule(CreditOffer creditOffer) {
        List<PaymentGraphic> paymentGraphics = new ArrayList<>();
        // Calculate the monthly interest rate
        double monthlyInterestRate = creditOffer.getCredit().getInterestRate() / 1200.0;  // Assuming interest rate is in percentage

        // Calculate the monthly payment amount
        double monthlyPayment = calculateMonthlyPayment(creditOffer.getCreditSum(), monthlyInterestRate, creditOffer.getMonthsOfCredit());

        // Use the startDate directly from the CreditOffer object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creditOffer.getStartDate());

        double remainingBalance = creditOffer.getCreditSum();

        for (int month = 1; month <= creditOffer.getMonthsOfCredit(); month++) {
            // Calculate payment date
            calendar.add(Calendar.MONTH, 1);
            Date paymentDate = calendar.getTime();

            // Calculate credit body (principal payment)
            double creditBody = calculateCreditBody(monthlyPayment, monthlyInterestRate, remainingBalance);
            Long creditBodyLong = Math.round(creditBody); // Convert to long

            // Calculate credit percents (interest payment)
            double creditPercents = monthlyPayment - creditBody;
            Long creditPercentsLong = Math.round(creditPercents); // Convert to long

            // Calculate remains (remaining balance)
            remainingBalance -= creditBody;
            Long remains = Math.round(remainingBalance); // Convert to long

            PaymentGraphic paymentGraphic = new PaymentGraphic(creditOffer, paymentDate, Math.round(monthlyPayment), creditBodyLong, creditPercentsLong, remains);
            paymentGraphics.add(paymentGraphic);
        }
        return paymentGraphics;
    }

// Helper methods for calculations (adjust based on your specific interest calculation method)

    private double calculateMonthlyPayment(double loanAmount, double monthlyInterestRate, long numberOfMonths) {
        return (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths)) / (Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1);
    }

    private double calculateCreditBody(double monthlyPayment, double monthlyInterestRate, double remainingBalance) {
        return monthlyPayment - (remainingBalance * monthlyInterestRate);
    }
}

