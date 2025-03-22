package ru.ahibis.musicreviewbot.controllers.commands;

import ru.ahibis.musicreviewbot.util.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IBotCommand {
    boolean canRun(ParsedText parsedText);
    String run(ParsedText parsedText, Message message);

}
