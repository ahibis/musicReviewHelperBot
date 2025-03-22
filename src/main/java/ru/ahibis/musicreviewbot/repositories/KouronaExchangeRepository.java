package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.KorunaExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KouronaExchangeRepository extends JpaRepository<KorunaExchange, Long> {

}