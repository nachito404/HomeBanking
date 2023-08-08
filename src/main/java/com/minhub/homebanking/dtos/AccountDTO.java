package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {
    private Long id;

    private String number;

    private LocalDate date;

    private double balance;

    public AccountDTO (Account account){
        id = account.getId();
        number = account.getNumber();
        date = account.getCreationDate();
        balance = account.getBalance();
    }


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }
}
