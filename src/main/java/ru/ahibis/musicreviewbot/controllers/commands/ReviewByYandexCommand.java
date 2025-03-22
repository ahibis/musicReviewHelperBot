package ru.ahibis.musicreviewbot.controllers.commands;


import ru.ahibis.musicreviewbot.services.albumReview.AlbumDTO;
import ru.ahibis.musicreviewbot.services.albumReview.AlbumReview;
import ru.ahibis.musicreviewbot.services.AlbumService;
import ru.ahibis.musicreviewbot.services.yandex.YandexMusicService;
import ru.ahibis.musicreviewbot.services.yandex.getAlbum.Album;
import ru.ahibis.musicreviewbot.util.textParser.ParsedText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.net.URI;
import java.util.Arrays;

public class ReviewByYandexCommand implements IBotCommand {

    private final AlbumService albumService;
    private final YandexMusicService yandexService;

    public ReviewByYandexCommand(AlbumService albumService, YandexMusicService yandexService) {
        this.albumService = albumService;
        this.yandexService = yandexService;
    }

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
        Long userId = message.getFrom().getId();
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
        Album album = yandexService.getAlbum(APiURL);
        if (album == null) {
            return "не удалось получить данные об альбоме";
        }
        var albumData = getAlbumData(album, marks);
        Long MyAlbumId = albumService.saveAlbum(albumData, userId);

        var albumReview = new AlbumReview(albumData);
        albumService.markAlbum(MyAlbumId, albumReview.getEstimate(), userId);

        return albumReview.makeReview();
    }


}
