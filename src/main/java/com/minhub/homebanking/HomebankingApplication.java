package com.minhub.homebanking;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.minhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args -> {
			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Transaction transaction1 = new Transaction(TransactionType.DEBITO, -90000.00, "motorcycle spare part", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDITO, 50000.00, "furniture", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDITO, 5000.00, "chair", LocalDateTime.now());

			clientRepository.save(client);

			client.addAccount(account1);
			client.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account2);

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account2.addTransaction(transaction3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
		};
	}
}
