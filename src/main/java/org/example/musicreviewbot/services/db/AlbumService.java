package org.example.musicreviewbot.services.db;

import org.example.musicreviewbot.models.Album;
import org.example.musicreviewbot.models.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    // Другие методы
}