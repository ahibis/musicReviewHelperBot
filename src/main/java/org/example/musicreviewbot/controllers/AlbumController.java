package org.example.musicreviewbot.controllers;
import org.example.musicreviewbot.models.Album;
import org.example.musicreviewbot.repositories.dto.AlbumStatsDTO;
import org.example.musicreviewbot.repositories.dto.AlbumWithTracksDTO;
import org.example.musicreviewbot.services.AlbumService;
import org.example.musicreviewbot.services.TokenService;
import org.example.musicreviewbot.services.yandex.YandexMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin()
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private YandexMusicService yandexMusicService;

    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.saveAlbum(album);
    }

    @GetMapping("/{id}")
    public Album getAlbum(@PathVariable Long id) {
        return albumService.getAlbumById(id);
    }

    @GetMapping("")
    public List<AlbumStatsDTO> getAlbumService() {
        return albumService.getAllAlbums();
    }
    @GetMapping("/withTracks/{id}")
    public Optional<AlbumWithTracksDTO> getAlbumWithTracks(@PathVariable Long id) {
        return albumService.getAlbumWithTracks(id);
    }
    @PostMapping("/my")
    public List<AlbumStatsDTO> getMyAlbums(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if(token == null){
            return List.of();
        }
        Long userId = tokenService.getUserByTgToken(token);
        if(userId == null){
            return List.of();
        }
        return albumService.getAlbumReviews(userId);
    }

    @GetMapping("/yandex/{albumId}")
    public org.example.musicreviewbot.services.yandex.getAlbum.Album getAlbumWithTracks(@PathVariable("albumId") String albumId) {
        return yandexMusicService.getAlbum("https://music.yandex.ru/handlers/album.jsx?album=" + albumId);
    }
}