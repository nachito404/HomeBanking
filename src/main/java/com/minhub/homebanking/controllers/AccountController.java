package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.AccountDTO;
import com.minhub.homebanking.dtos.ClientDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.minhub.homebanking.utils.Utils;
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
//Por defecto el request mapping es de tipo get
@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccount(){
        List<Account> listAccount = accountRepository.findAll();
        List<AccountDTO> listAccountDTO = listAccount.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
        return listAccountDTO;
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> addAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().toArray().length >= 3) {
            return new ResponseEntity<>("maximum number of accounts reached", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account("VIN" + getUniqueAccountNumber(), LocalDate.now(), 0);
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        }

    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    public int getUniqueAccountNumber() {
        int randomNumber;
        do {
            randomNumber = Utils.getAccountNumber();
        }while(accountRepository.findByNumber("VIN-"+randomNumber)!=null);
        return randomNumber;
    }
}
