package ru.ahibis.musicreviewbot.controllers.commands;


import ru.ahibis.musicreviewbot.util.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public class NotFoundedCommand implements IBotCommand {
    @Override
    public boolean canRun(ParsedText parsedText) {
        return true;
    }

    @Override
    public String run(ParsedText parsedText, Message message) {
        return "Команда не найдена\nнапишите помощь, чтобы увидеть весь список команд";
    }
}
