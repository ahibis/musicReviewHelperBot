package ru.ahibis.musicreviewbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.ahibis.musicreviewbot.config.RestClientConfiguration;
import ru.ahibis.musicreviewbot.repositories.KouronaExchangeRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KouronaExchangeService {
    @Autowired
    KouronaExchangeRepository kouronaExchangeRepository;

    @Autowired
    RestClientConfiguration restClientConfig;

    void SaveTodayKoronaExchange() {
        LocalDate today = LocalDate.now();
        String dateString = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String result = Objects.requireNonNull(restClientConfig
                        .CZKRestClient()
                        .get()
                        .uri("daily.txt?date=" + dateString)
                        .retrieve()
                        .body(String.class))
                .lines()
                .skip(1)
                .collect(Collectors.joining("\n"));



    }
}
