package org.example.musicreviewbot.service.help;


import org.example.musicreviewbot.service.IBotCommand;
import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HelpCommand implements IBotCommand {
    @Override
    public boolean canRun(ParsedText parsedText) {
        var text = parsedText.getText();
        return text.contains("help") || text.contains("помощь");
    }

    @Override
    public String run(ParsedText parsedText, Message message) {
        return """
                Все команды:
                👉Оценка яндекс альбома(запускается при наличии ссылки на альбом в яндекс музыке):
                Вызов: [ссылка на альбом] [оценки через пробел или запятую]
                Выдает форматированный обзор на альбом с оценкой за весь альбом по формуле корень(сумма(оценка^2)/кол-во оценок).
                """;
    }
}
