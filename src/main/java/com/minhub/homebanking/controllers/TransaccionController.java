package com.minhub.homebanking.controllers;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.minhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransaccionController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

@Transactional
@PostMapping("/transactions")
    public ResponseEntity<Object> transactions(
        @RequestParam String fromAccountNumber,@RequestParam String toAccountNumber,
        @RequestParam Double amount , @RequestParam String description,
        Authentication authentication){
    Client client = clientRepository.findByEmail(authentication.getName());
    Account account = accountRepository.findByNumber(fromAccountNumber);
    Account account1 = accountRepository.findByNumber(toAccountNumber);
    if (amount == 0 || description.isBlank() || fromAccountNumber.isBlank() || toAccountNumber.isBlank()){
        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

    } else if (fromAccountNumber.equals(toAccountNumber)) {
        return new ResponseEntity<>("the destination account cannot be the same as the current one", HttpStatus.FORBIDDEN);

    } else if (client.getAccounts().stream().filter(number -> number.getNumber().equals(account)) == null) {
        return new ResponseEntity<>("this is not your account number", HttpStatus.FORBIDDEN);

    } else if (account1 == null) {
        return new ResponseEntity<>("destination account doesn't exist", HttpStatus.FORBIDDEN);

    } else if (account.getBalance()<amount) {
        return new ResponseEntity<>("insufficient funds", HttpStatus.FORBIDDEN);

    } else{
        Transaction transactionFrom = new Transaction(TransactionType.DEBITO, amount, description + " " + toAccountNumber , LocalDateTime.now());
        Transaction transactionTo = new Transaction(TransactionType.CREDITO, amount, description + " " + fromAccountNumber , LocalDateTime.now());
        account.addTransaction(transactionFrom);
        account1.addTransaction(transactionTo);
        transactionRepository.save(transactionFrom);
        transactionRepository.save(transactionTo);
        account.setBalance(account.getBalance()-amount);
        account1.setBalance(account1.getBalance()+amount);
        accountRepository.save(account);
        accountRepository.save(account1);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
}
}
}

