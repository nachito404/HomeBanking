package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.AccountDTO;
import com.minhub.homebanking.dtos.ClientDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.sun.source.tree.DoWhileLoopTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
    public ResponseEntity<Object> addAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().toArray().length >= 3) {
            return new ResponseEntity<>("maximum number of accounts reached", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account("VIN" + getRandomNumber(10000000, 100000000), LocalDate.now(), 0);
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        }

    }
    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    public int getRandomNumber(int min, int max) {
        int randomNumber;
        do {
            randomNumber = (int) ((Math.random() * (max - min)) + min);
        }while(accountRepository.findByNumber("VIN-"+randomNumber)!=null);

        return randomNumber;
    }
}
