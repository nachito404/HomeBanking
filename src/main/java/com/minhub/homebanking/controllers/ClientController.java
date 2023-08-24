package com.minhub.homebanking.controllers;

import com.minhub.homebanking.dtos.ClientDTO;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repositories.ClientRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    //lo que esta debajo es un servlet, siempre van a encontrarse dentro de un controlador.
    @RequestMapping("/clients")
    public List<ClientDTO> getClient() {
        List<Client> listClient = clientRepository.findAll();
        List<ClientDTO> listClientDTO = listClient.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
        return listClientDTO;
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {

        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));

    }
        @Autowired
        private PasswordEncoder passwordEncoder;

        @RequestMapping(path = "/clients", method = RequestMethod.POST)

        public ResponseEntity<Object> register(
                @RequestParam String firstName, @RequestParam String lastName,
                @RequestParam String email, @RequestParam String password) {
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if (clientRepository.findByEmail(email) != null) {
                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
            }

            clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

            return new ResponseEntity<>(HttpStatus.CREATED);

        }
    }

