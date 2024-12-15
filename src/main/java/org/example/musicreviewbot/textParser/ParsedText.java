package org.example.musicreviewbot.textParser;




import lombok.Getter;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParsedText {
    ArrayList<TextToken> tokens = new ArrayList<>();
    @Getter
    String text;
    ArrayList<TextToken> getTokensByText(String text) {
        String regex = "[^,\\s]+";
        this.text = text;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        ArrayList<TextToken> tokens = new ArrayList<>();

        while (matcher.find()) {
            TextToken token = new TextToken(matcher.group());
            tokens.add(token);
        }
        return tokens;
    }

    public ParsedText(String text) {
        tokens = getTokensByText(text);
    }
    public ArrayList<Double> getNumbers(){
        return (ArrayList<Double>) tokens.stream()
                .filter(TextToken::isNumber)
                .map(TextToken::getDouble)
                .collect(Collectors.toList());
    }
    public ArrayList<URI> getUrls(){
        return (ArrayList<URI>) tokens.stream()
                .filter(TextToken::isUrl)
                .map(TextToken::getUrl)
                .collect(Collectors.toList());
    }
    public URI getURL(){
        return tokens.stream().filter(TextToken::isUrl).map(TextToken::getUrl).findFirst().orElse(null);
    }

    public ArrayList<String> getStrings(){
        return (ArrayList<String>) tokens.stream().map(TextToken::getString).collect(Collectors.toList());
    }
}
