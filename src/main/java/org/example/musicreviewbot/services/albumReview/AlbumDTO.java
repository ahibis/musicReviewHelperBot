package org.example.musicreviewbot.services.albumReview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlbumDTO {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;
}
