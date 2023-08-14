package com.minhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy = "native", name = "native")
    private Long id;

    private Double amount;

    private Integer payments;
@ManyToOne(fetch = FetchType.EAGER)
    private Client client;
@ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;

    //constructores


    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payments) {
        this.amount = amount;
        this.payments = payments;
    }


    //metodos


    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                ", client=" + client +
                ", loan=" + loan +
                '}';
    }
}
