package com.minhub.homebanking;

import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personnel"))));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

   @Test
    public void existCreditCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("type", is(CardType.CREDIT))));
    }

    @Test
    public void clientExist(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void clientPaswordIsNotEmpty(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("pasword", not(emptyOrNullString()))));
    }

    @Test
    public void transactionExist(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }

    @Test
    public void transactionAmountNotNull(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,hasItem(hasProperty("amount", notNullValue())));
    }

    @Test
    public void accountExist(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void accountCreationDateExist(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,hasItem(hasProperty("creationDate",notNullValue())));
    }
}
