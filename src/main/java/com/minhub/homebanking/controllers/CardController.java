package com.minhub.homebanking.controllers;

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

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> addCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(TYPE -> TYPE.getType().equals(CardType.DEBIT)).toArray().length>=3){
            return new ResponseEntity<>("maximum number of cards debit reached", HttpStatus.FORBIDDEN);
        } else if (client.getCards().stream().filter(TYPE -> TYPE.getType().equals(CardType.CREDIT)).toArray().length>=3) {
            return new ResponseEntity<>("maximum number of cards credit reached", HttpStatus.FORBIDDEN);
        } else{
            Card card = new Card(client.getFirstName()+" "+client.getLastName(),cardType,cardColor,getRandomNumber(1000, 10000)+"-"+getRandomNumber(1000, 10000)+"-"+getRandomNumber(1000, 10000)+"-"+getRandomNumber(1000, 10000), getRandomNumber(100,1000), LocalDate.now().plusYears(5),LocalDate.now());
            client.addCard(card);
            cardRepository.save(card);
            return new ResponseEntity<>("card created successfully",HttpStatus.CREATED);
        }
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
