package org.example.musicreviewbot.repositories.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.musicreviewbot.models.Album;
import org.example.musicreviewbot.models.Track;

import java.util.List;

@Setter
@Getter
public class AlbumWithTracksDTO {
    // Геттер и сеттер для tracks
    private Long id;
    private String title;
    private Long authorId;
    private List<Track> tracks;

    public AlbumWithTracksDTO(Long id, String title, Long authorId, List<Track> tracks) {
        this.setId(id);
        this.setTitle(title);
        this.setAuthorId(authorId);
        this.tracks = tracks;
    }

}