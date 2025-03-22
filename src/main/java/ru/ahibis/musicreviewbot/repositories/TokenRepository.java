package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token getByToken(String token);
}