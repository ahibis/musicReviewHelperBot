package org.example.musicreviewbot.service.yandex;


import com.google.gson.Gson;
import org.example.musicreviewbot.service.IBotCommand;
import org.example.musicreviewbot.service.albumReview.AlbumData;
import org.example.musicreviewbot.service.albumReview.AlbumReview;
import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewByYandexCommand implements IBotCommand {
    @Override
    public boolean canRun(ParsedText parsedText) {
        List<URI> urls = parsedText.getUrls();
        if (urls.isEmpty()) return false;
        URI uri = urls.getFirst();
        String host = uri.getHost();
        if (host.equals("music.yandex.ru")) {
            return true;
        }
        return false;
    }

    private AlbumData getAlbumData(Album album, ArrayList<Double> marks) {
        var tracks = (ArrayList<String>) Arrays.stream(album.volumes[0]).map(track -> track.title).collect(Collectors.toList());
        return new AlbumData(album.title, album.artists[0].name, tracks, marks);
    }

    @Override
    public String run(ParsedText parsedText, Message message) {
        var marks = parsedText.getNumbers();
        var URI = parsedText.getURL();
        System.out.println(URI);
        System.out.println(marks);
        var albumID = Arrays
                .stream(URI.getPath().split("/"))
                .filter(val -> val.matches("\\d+"))
                .map(v -> Integer.parseInt(v))
                .findFirst();
        if (albumID.isEmpty()) {
            return "Не верно указана ссылка на яндекс альбом";
        }
        var albumId = albumID.get().intValue();
        System.out.println(albumId);
        var APiURL = "https://music.yandex.ru/handlers/album.jsx?album=" + albumId;
        Album album = getAlbum(APiURL);
        if (album == null) {
            return "не удалось получить данные об альбоме";
        }
        var albumData = getAlbumData(album, marks);
        var albumReview = new AlbumReview(albumData);

        return albumReview.makeReview();
    }

    private Album getAlbum(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            if (!conn.getResponseMessage().equals("OK")) {
                return null;
            }
            System.out.println(conn.getResponseMessage());

            Gson gson = new Gson();
            return gson.fromJson(reader, Album.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}