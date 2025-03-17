package org.example.musicreviewbot.controllers;
import org.example.musicreviewbot.models.Album;
import org.example.musicreviewbot.repositories.dto.AlbumStatsDTO;
import org.example.musicreviewbot.repositories.dto.AlbumWithTracksDTO;
import org.example.musicreviewbot.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin()
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

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
}