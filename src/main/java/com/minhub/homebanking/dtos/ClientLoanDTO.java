package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    //atributos
    private Long id;

    private Long loanId;

    private String name;

    private Double amount;

    private Integer payments;

    //constructores

    public ClientLoanDTO(ClientLoan clientLoan) {
        id = clientLoan.getId();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
    }


    //metodos


    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
