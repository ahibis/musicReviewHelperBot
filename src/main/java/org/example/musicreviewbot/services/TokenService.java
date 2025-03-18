package org.example.musicreviewbot.services;

import org.example.musicreviewbot.models.Token;
import org.example.musicreviewbot.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;


    public String generateAuthByTgToken(Long userId) {
        Token token = new Token();
        token.setType("telegramAuth");
        token.setScope("telegramId:"+userId.toString());
        String tokenStr = UUID.randomUUID().toString();
        token.setExpires(LocalDateTime.now().plusDays(1));
        token.setToken(tokenStr);
        tokenRepository.save(token);
        return tokenStr;
    }

    public static Long extractTelegramId(String input) {
        if (input == null || !input.startsWith("telegramId:")) {
            return null;
        }

        String[] parts = input.split(":"); // Разделяем строку по ":"
        if (parts.length != 2) {
            return null;
        }

        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long getUserByTgToken(String tokenStr) {
        Token token = tokenRepository.getByToken(tokenStr);
        if(token == null) {
            return null;
        }
        if(token.getType().equals("telegramAuth")) {
            return extractTelegramId(token.getScope());
        }
        return null;
    }
}
