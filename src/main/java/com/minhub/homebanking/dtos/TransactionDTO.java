package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    //atributos
    private Long id;

    private TransactionType type;

    private Double amount;

    private String description;

    private LocalDateTime date;

    //constructores


    public TransactionDTO(Transaction transaction) {
        id = transaction.getId();
        type = transaction.getType();
        amount = transaction.getAmount();
        description = transaction.getDescription();
        date = transaction.getDate();
    }


    //metodos
    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
