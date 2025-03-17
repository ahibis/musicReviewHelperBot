package org.example.musicreviewbot.services;

import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IBotCommand {
    boolean canRun(ParsedText parsedText);
    String run(ParsedText parsedText, Message message);

}
