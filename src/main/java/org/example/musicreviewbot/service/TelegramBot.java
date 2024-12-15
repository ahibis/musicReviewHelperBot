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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

            Arrays.stream(botCommands).anyMatch(command -> {
                boolean canRun = command.canRun(parsedText);
                if (canRun) {
                    String answer = command.run(parsedText, message);
                    this.sendMessage(chatId, answer);
                }
                return canRun;
            });
        }
    }

    private void startCommandRecieved(long chatId, String userName) {
        log.info("start message recieved for chatId: userName: {}", userName);
        String answer = "Привет" + userName;
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf((chatId)));
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
