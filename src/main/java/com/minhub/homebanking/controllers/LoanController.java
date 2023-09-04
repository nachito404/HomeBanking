package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.LoanApplicationDTO;
import com.minhub.homebanking.dtos.LoanDTO;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<Loan> listLoan = loanRepository.findAll();
        List<LoanDTO> listLoanDTO = listLoan.stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
        return listLoanDTO;

    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> getLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        if (loanApplicationDTO.getAmount()<=0|| loanApplicationDTO.getPayments()<=0||loanApplicationDTO.getLoanId()<=0||loanApplicationDTO.getToAccountNumber()=="VIN"){
            return new ResponseEntity<>("missing data", HttpStatus.FORBIDDEN);
        } else if (loan == null) {
            return new ResponseEntity<>("this loan doesn't exist", HttpStatus.FORBIDDEN);
        } else if (loanApplicationDTO.getAmount()>loan.getMaxAmount()) {
            return new ResponseEntity<>("the amount requested exceeds the maximum amount of the loan", HttpStatus.NOT_ACCEPTABLE);
        } else if (loan.getPayments().stream().equals(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("the number of quotas requested are not within the quotas available",HttpStatus.NOT_ACCEPTABLE);
        } else if (client.getAccounts().stream().filter(Account -> Account.getNumber().equals(account))== null) {
            return new ResponseEntity<>("this is not your account number", HttpStatus.FORBIDDEN);
        }else {
            ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount()*0.2,loanApplicationDTO.getPayments());
            Transaction transaction = new Transaction(TransactionType.CREDITO,loanApplicationDTO.getAmount(),loan.getName() +" "+ "Loan Approved", LocalDateTime.now());
            account.addTransaction(transaction);
            client.addClientLoan(clientLoan);
            loan.addClientLoan(clientLoan);
            account.setBalance(account.getBalance()+loanApplicationDTO.getAmount());
            clientLoanRepository.save(clientLoan);
            transactionRepository.save(transaction);
        }
        return new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);
    }
}
