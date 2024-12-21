package org.example.musicreviewbot.service.yandex;


import com.google.gson.Gson;
import org.example.musicreviewbot.service.IBotCommand;
import org.example.musicreviewbot.service.albumReview.AlbumDTO;
import org.example.musicreviewbot.service.albumReview.AlbumReview;
import org.example.musicreviewbot.service.yandex.getAlbum.Album;
import org.example.musicreviewbot.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

public class ReviewByYandexCommand implements IBotCommand {
    @Override
    public boolean canRun(ParsedText parsedText) {
        URI[] urls = parsedText.getURIs();
        if (urls.length == 0) return false;
        URI uri = urls[0];
        String host = uri.getHost();

        if (host.equals("music.yandex.ru") || host.equals("music.yandex.com")) {
            return true;
        }
        return false;
    }

    private AlbumDTO getAlbumData(Album album, double[] marks) {
        var tracks = Arrays.stream(album.volumes[0]).map(track -> track.title).toArray(String[]::new);
        return new AlbumDTO(album.title, album.artists[0].name, tracks, marks);
    }

    @Override
    public String run(ParsedText parsedText, Message message) {
        var marks = parsedText.getNumbers();
        var URI = parsedText.getURI();
        System.out.println(URI);
        System.out.println(marks);
        var albumID = Arrays
                .stream(URI.getPath().split("/"))
                .filter(val -> val.matches("\\d+"))
                .map(Integer::parseInt)
                .findFirst();
        if (albumID.isEmpty()) {
            return "Не верно указана ссылка на яндекс альбом";
        }
        var albumId = albumID.get();
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
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return null;
                //throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            try(InputStreamReader reader = new InputStreamReader(conn.getInputStream())){
                System.out.println(conn.getResponseMessage());

                Gson gson = new Gson();
                return gson.fromJson(reader, Album.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        // Закрываем HttpURLConnection вручную
        if (conn != null) {
            conn.disconnect();
        }
    }

    }
}
