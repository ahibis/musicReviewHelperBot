package org.example.musicreviewbot.controllers;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.musicreviewbot.config.botConfig;
import org.example.musicreviewbot.controllers.commands.IBotCommand;
import org.example.musicreviewbot.controllers.commands.NotFoundedCommand;
import org.example.musicreviewbot.services.AlbumService;
import org.example.musicreviewbot.controllers.commands.HelpCommand;
import org.example.musicreviewbot.controllers.commands.ReviewByYandexCommand;
import org.example.musicreviewbot.services.yandex.YandexMusicService;
import org.example.musicreviewbot.textParser.ParsedText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Arrays;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final botConfig config;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private YandexMusicService yandexService;

    public TelegramBot(botConfig config) {
        this.config = config;
    }

    private IBotCommand[] botCommands;
    @PostConstruct
    public void init() {
        // Инициализация команд после внедрения зависимостей
        botCommands = new IBotCommand[]{
                new HelpCommand(),
                new ReviewByYandexCommand(albumService, yandexService),
                new NotFoundedCommand()
        };
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            String messageText = message.getText();
            long chatId = message.getChatId();
            ParsedText parsedText = new ParsedText(messageText);

            var chooseCommand = Arrays.stream(botCommands)
                    .filter(command -> command.canRun(parsedText))
                    .findFirst().orElse(null);
            try {
                assert chooseCommand != null;
                String answer = chooseCommand.run(parsedText, message);
                this.sendMessage(chatId, answer);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                sendMessage(chatId, "Непредвиденная ошибка\n" + e.getMessage());
            }
        }
    }

    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error message {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
