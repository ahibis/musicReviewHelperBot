package org.example.musicreviewbot.service;

import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IBotCommand {
    public boolean canRun(ParsedText parsedText);
    public String run(ParsedText parsedText, Message message);

}