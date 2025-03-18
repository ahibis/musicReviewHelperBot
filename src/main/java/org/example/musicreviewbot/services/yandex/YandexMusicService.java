package org.example.musicreviewbot.services.yandex;

import com.google.gson.Gson;
import org.example.musicreviewbot.services.yandex.getAlbum.Album;
import org.springframework.stereotype.Service;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class YandexMusicService {

    public Album getAlbum(String urlString) {
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
            try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
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
