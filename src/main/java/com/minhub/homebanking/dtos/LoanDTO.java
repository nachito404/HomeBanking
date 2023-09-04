package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoanDTO {
    //atributos
    private Long id;

    private String name;

    private Double maxAmount;

    private Set<Integer> payments;


    //constructores

    public LoanDTO(Loan loan) {
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();
    }


    //metodos

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Set<Integer> getPayments() {
        return payments;
    }
}
