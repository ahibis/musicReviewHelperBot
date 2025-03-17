package org.example.musicreviewbot.services;

import org.example.musicreviewbot.models.Track;
import org.example.musicreviewbot.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    public List<Track> getTracksByAlbum(Long albumId) {
        return trackRepository.findByAlbumId(albumId);
    }
}
