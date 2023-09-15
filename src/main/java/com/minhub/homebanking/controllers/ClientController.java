package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.ClientDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.AccountRepository;
import com.minhub.homebanking.repositories.ClientRepository;
import com.minhub.homebanking.utils.Utils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //servlet asocia la peticion a la ruta
    //lo que esta debajo es un servlet, siempre van a encontrarse dentro de un controlador.
    @GetMapping("/clients")
    public List<ClientDTO> getClient() {
        List<Client> listClient = clientRepository.findAll();
        List<ClientDTO> listClientDTO = listClient.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return listClientDTO;
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank()) {
            return new ResponseEntity<>("need to enter the name", HttpStatus.NO_CONTENT);
        } else if (lastName.isBlank()) {
            return new ResponseEntity<>("need to enter the lastname", HttpStatus.NO_CONTENT);
        } else if (email.isBlank()) {
            return new ResponseEntity<>("need to enter the email", HttpStatus.NO_CONTENT);
        } else if (password.isBlank()) {
            return new ResponseEntity<>("need to enter the password", HttpStatus.NO_CONTENT);
        } else if (firstName.length() > 15 || lastName.length() > 15) {
            return new ResponseEntity<>("name exceeds number of characters", HttpStatus.NOT_ACCEPTABLE);
        } else if (password.length() < 8 || password.length() > 30) {
            return new ResponseEntity<>("password must be between 8 and 30 characters", HttpStatus.NOT_ACCEPTABLE);
        } else if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        } else {
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
            Account account = new Account("VIN" + Utils.getAccountNumber(), LocalDate.now(), 0);
           clientRepository.save(client);
           client.addAccount(account);
           accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
