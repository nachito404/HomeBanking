package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;

public class LoanApplicationDTO {
    //atributos
    private Long loanId;

    private Double amount;

    private Integer payments;

    private String toAccountNumber;

    //constructores

    public LoanApplicationDTO(Long loanId, Double amount, Integer payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    //metodos

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
