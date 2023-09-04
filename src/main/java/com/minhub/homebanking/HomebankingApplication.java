package com.minhub.homebanking;

import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("654321"));
			Client client1 = new Client("Juan", "Perez", "juancitoP@gmail.com", passwordEncoder.encode("123456"));

			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN003", LocalDate.now(), 20000);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO, -90000.00, "motorcycle spare part", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDITO, 50000.00, "furniture", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDITO, 5000.00, "chair", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBITO, -4500.00, "icecream", LocalDateTime.now());

			Loan mortgage = new Loan("Mortgage", 500000.00);
					mortgage.setPayments(Set.of(12,24,36,48,60));
            Loan personnel = new Loan("Personnel", 100000.00);
					personnel.setPayments(Set.of(6,12,24));
			Loan automotive = new Loan("Automotive", 300000.00);
					automotive.setPayments(Set.of(6,12,24,36));

			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60);
			ClientLoan clientLoan2 = new ClientLoan(50000.00,12);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, 24);
			ClientLoan clientLoan4 = new ClientLoan(200000.00,36);

			Card card1 = new Card(client.getFirstName()+ " "+client.getLastName(), CardType.CREDIT, CardColor.GOLD, "5544-8486-5445-8684", 534, LocalDate.now().plusYears(5), LocalDate.now());
            Card card2 = new Card(client.getFirstName()+ " "+client.getLastName(), CardType.DEBIT, CardColor.TITANIUM, "5544-9891-5445-1878", 435, LocalDate.now().plusYears(5), LocalDate.now());
			Card card3 = new Card(client1.getFirstName()+ " "+client1.getLastName(), CardType.CREDIT, CardColor.SILVER, "5544-8074-1445-7642", 489, LocalDate.now().plusYears(5), LocalDate.now());

			clientRepository.save(client);
			clientRepository.save(client1);

			client.addAccount(account1);
			client.addAccount(account2);
			client1.addAccount(account3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account3.addTransaction(transaction4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			loanRepository.save(mortgage);
			loanRepository.save(personnel);
			loanRepository.save(automotive);

			client.addClientLoan(clientLoan1);
			client.addClientLoan(clientLoan2);
			mortgage.addClientLoan(clientLoan1);
			personnel.addClientLoan(clientLoan2);

			client1.addClientLoan(clientLoan3);
			client1.addClientLoan(clientLoan4);
			personnel.addClientLoan(clientLoan3);
			automotive.addClientLoan(clientLoan4);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			client.addCard(card1);
			client.addCard(card2);
			client1.addCard(card3);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
	}
}
