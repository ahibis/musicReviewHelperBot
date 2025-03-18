package org.example.musicreviewbot.controllers.commands;


import org.aspectj.bridge.IMessageHandler;
import org.example.musicreviewbot.services.TokenService;
import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AuthCommand implements IBotCommand {

    TokenService tokenService;
    public AuthCommand(TokenService service) {
        tokenService = service;
    }

    @Override
    public boolean canRun(ParsedText parsedText) {
        var text = parsedText.getText();
        return text.contains("авторизоваться") || text.contains("auth");
    }

    @Override
    public String run(ParsedText parsedText, Message message) {
        Long userId = message.getFrom().getId();
        String token = tokenService.generateAuthByTgToken(userId);
        return "Перейдите по ссылке чтобы авторизоваться на сайте http://localhost:3000/?token=" + token;
    }
}
