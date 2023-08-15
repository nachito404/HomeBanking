package com.minhub.homebanking.models;

import com.minhub.homebanking.dtos.ClientLoanDTO;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Client {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Card> cards = new HashSet<>();

    //constructoress

    public Client(){}

    public Client (String name,String lastName,String email){
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
    }

    //metodos


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount (Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public Set<ClientLoan> getLoans() {
        return clientLoans;
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    public Set<Card> getCards(){return cards; }

    public void addCard (Card card){
        card.setClient(this);
        this.cards.add(card);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
