package org.example.musicreviewbot.textParser;


import lombok.Getter;

import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParsedText {
    ArrayList<TextToken> tokens = new ArrayList<>();
    @Getter
    String text;
    double[] nums;
    URI[] uris;
    URI uri;
    String[] strings;

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

    public double[] getNumbers() {
        if (nums == null) {
            nums = tokens.stream()
                    .filter(TextToken::isNumber)
                    .mapToDouble(TextToken::getDouble)
                    .toArray();
        }
        return nums;
    }

    public URI[] getURIs() {
        if (uris == null) {
            uris = tokens.stream()
                    .filter(TextToken::isUrl)
                    .map(TextToken::getUrl)
                    .toArray(URI[]::new);
        }

        return uris;
    }

    public URI getURI() {
        if (uri == null) {
            uri = tokens.stream()
                    .filter(TextToken::isUrl)
                    .map(TextToken::getUrl)
                    .findFirst()
                    .orElse(null);
        }
        return uri;
    }

    public String[] getStrings() {
        if(strings == null) {
            strings =  tokens.stream().map(TextToken::getString).toArray(String[]::new);
        }
        return strings;
    }
}
