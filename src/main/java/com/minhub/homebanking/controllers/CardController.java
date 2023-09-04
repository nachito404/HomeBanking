package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.AccountDTO;
import com.minhub.homebanking.dtos.CardDTO;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.CardColor;
import com.minhub.homebanking.models.CardType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.CardRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> addCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(TYPE -> TYPE.getType() == cardType).toArray().length >= 3) {
            return new ResponseEntity<>("maximum number of cards reached", HttpStatus.FORBIDDEN);
        } else if (client.getCards().stream().filter(type -> type.getType() == cardType).filter(color -> color.getColor()== cardColor).toArray().length>=1 ) {
            return new ResponseEntity<>("you cannot have two cards of the same color", HttpStatus.FORBIDDEN);
        } else {
            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, getRandomNumberPlusFour(1000,10000),getRandomNumber(100,1000), LocalDate.now().plusYears(5), LocalDate.now());
            client.addCard(card);
            cardRepository.save(card);
            return new ResponseEntity<>("card created successfully", HttpStatus.CREATED);
        }
    }

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCard(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);}

    public String getRandomNumberPlusFour(int min, int max) {
        String randomNumber;
        do {
            randomNumber = getRandomNumber(min, max) + "-" + getRandomNumber(min, max) + "-" + getRandomNumber(min, max) + "-" + getRandomNumber(min, max);
        } while (cardRepository.findByNumber(randomNumber) != null);

        return randomNumber;
    }
}
