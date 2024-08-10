package org.example.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_graphics")
public class PaymentGraphic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creditOffer_id", nullable = false)
    private CreditOffer creditOffer;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_sum")
    private Long paymentSum;

    @Column(name = "credit_body")
    private Long creditBody;

    @Column(name = "credit_percents")
    private Long creditPercents;

    @Column(name = "remains")
    private Long remains;

    public PaymentGraphic(CreditOffer creditOffer, Date paymentDate, Long paymentSum, Long creditBody, Long creditPercents, Long remains) {
        this.creditOffer = creditOffer;
        this.paymentDate = paymentDate;
        this.paymentSum = paymentSum;
        this.creditBody = creditBody;
        this.creditPercents = creditPercents;
        this.remains = remains;
    }
}