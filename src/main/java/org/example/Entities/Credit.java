package org.example.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "credit_Limit")
    private Long creditLimit;

    @Column(name = "interest_rate")
    private Double interestRate;

    @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
    private Set<CreditOffer> creditOffers;
}