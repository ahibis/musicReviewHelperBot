package ru.ahibis.musicreviewbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient CZKRestClient() {
        return RestClient.create("https://www.cnb.cz/en/financial_markets/foreign_exchange_market/exchange_rate_fixing");
    }
}
