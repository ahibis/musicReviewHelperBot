package ru.ahibis.musicreviewbot.util.textParser;


import org.glassfish.grizzly.utils.Pair;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToken {
    String tokenString;
    public TextToken(String token) {
        tokenString = token;
    }
    String getString(){
        return tokenString;
    }
    public boolean isNumber(){
        return tokenString.matches("\\d+\\.?\\d*") || isEstimate();
    }
    public double getDouble(){
        if(isEstimate()){
            String regex = "\\d+\\.?\\d*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tokenString);
            matcher.find();
            return Double.parseDouble(matcher.group());
        }
        return Double.parseDouble(tokenString);
    }
    boolean isUrl(){
        return tokenString.startsWith("http");
    }
    URI getUrl(){
        try {
            var uri = new URI(tokenString);
            return uri;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    Pair<String, String> getKey(){
        if(!tokenString.matches("\\s+=\\s+")){
            return null;
        }
        String[] pair = tokenString.split("=");
        return new Pair<>(pair[0],pair[1]);
    }
    boolean isEstimate(){
        return tokenString.matches("\\d+\\s*/\\s*\\d+");
    }
}
