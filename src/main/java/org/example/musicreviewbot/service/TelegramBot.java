package org.example.musicreviewbot.service;

import lombok.extern.slf4j.Slf4j;
import org.example.musicreviewbot.config.botConfig;
import org.example.musicreviewbot.service.help.HelpCommand;
import org.example.musicreviewbot.service.yandex.ReviewByYandexCommand;
import org.example.musicreviewbot.textParser.ParsedText;
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

    public TelegramBot(botConfig config) {
        this.config = config;
    }

    IBotCommand[] botCommands = {
            new HelpCommand(),
            new ReviewByYandexCommand(),
            new NotFoundedCommand()
    };

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
