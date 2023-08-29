package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.AccountDTO;
import com.minhub.homebanking.dtos.ClientDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount(){
        List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> listAccountDTO = listAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return listAccountDTO;
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> addAccount(Authentication authentication){
         Client client = clientRepository.findByEmail(authentication.getName());
         Account account = new Account("VIN-"+getRandomNumber(100000000, 10000000), LocalDate.now(), 0 );
         if (client.getAccounts().toArray().length >= 3) {
             return new ResponseEntity<>("maximum number of accounts reached", HttpStatus.FORBIDDEN);
         } else {
             client.addAccount(account);
             accountRepository.save(account);
             return new ResponseEntity<>("created", HttpStatus.CREATED);
         }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
