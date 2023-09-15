package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.AccountDTO;
import com.minhub.homebanking.dtos.CardDTO;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.CardColor;
import com.minhub.homebanking.models.CardType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.CardRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.minhub.homebanking.utils.Utils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
            int cvv = Utils.getCVV();
            Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor,getUniqueCardNumber() ,cvv, LocalDate.now().plusYears(5), LocalDate.now());
            client.addCard(card);
            cardRepository.save(card);
            return new ResponseEntity<>("card created successfully", HttpStatus.CREATED);
        }
    }


    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCard(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }




    public String getUniqueCardNumber() {
        String  unique;
        do {
             unique = Utils.getCardNumber();
        } while (cardRepository.findByNumber(unique) != null);

        return unique;
    }
}
