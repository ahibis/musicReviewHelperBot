package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token getByToken(String token);
}