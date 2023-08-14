package com.minhub.homebanking.dtos;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    //atributos
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;


    //constructores

    public ClientDTO(Client client){
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        loans = client.getLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
    }

    //metodos

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {return loans;}
}
