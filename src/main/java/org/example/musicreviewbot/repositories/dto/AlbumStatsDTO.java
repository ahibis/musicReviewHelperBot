package org.example.musicreviewbot.repositories.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumStatsDTO {
    private Long albumId;
    private String title;
    private Double averageRating;
    private String userName;

    public AlbumStatsDTO(Long albumId, String title,  Double averageRating, String userName) {
        this.albumId = albumId;
        this.title = title;
        this.averageRating = averageRating;
        this.userName = userName;
    }

}
