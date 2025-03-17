package org.example.musicreviewbot.controllers;
import org.example.musicreviewbot.models.Album;
import org.example.musicreviewbot.services.db.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
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
    public List<Album> getAlbumService() {
        return albumService.getAllAlbums();
    }
}