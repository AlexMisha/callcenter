package com.shepard.callcenter.controller;

import com.shepard.callcenter.dao.CDRRepository;
import com.shepard.callcenter.dao.ClientRepository;
import com.shepard.callcenter.entity.CDR;
import com.shepard.callcenter.entity.Client;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex Misha
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepository;
    private final CDRRepository cdrRepository;

    public ClientController(ClientRepository clientRepository, CDRRepository cdrRepository) {
        this.clientRepository = clientRepository;
        this.cdrRepository = cdrRepository;
    }

    @GetMapping("/findNotCalledToday")
    public List<Client> findNotCalledToday() {
        final LocalDate now = LocalDate.now(ZoneId.of("Europe/Moscow"));
        final Set<String> clientPhones = cdrRepository.findByCallDateBetween(
                Timestamp.from(now.atStartOfDay().toInstant(ZoneOffset.UTC)),
                Timestamp.from(now.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC))
        )
                .stream()
                .map(CDR::getDst)
                .collect(Collectors.toSet());

        if (clientPhones.isEmpty()) {
            return clientRepository.findAll();
        }

        return clientRepository.findByDoNotCallIsNullOrDoNotCallIsFalseAndPhoneNotIn(clientPhones);
    }

    @GetMapping("/findNeverCalled")
    public List<Client> findNeverCalled() {
        final List<Client> clients = clientRepository.findAll();

        final Set<String> clientPhones = cdrRepository.findByDstIn(clients.stream().map(Client::getPhone).collect(Collectors.toList()))
                .stream()
                .map(CDR::getDst)
                .collect(Collectors.toSet());
        if (clientPhones.isEmpty()) {
            return clients;
        }

        return clients.stream().filter(client -> !clientPhones.contains(client.getPhone())).collect(Collectors.toList());
    }
}
