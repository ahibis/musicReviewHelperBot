package org.example.musicreviewbot.services.albumReview;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlbumDTO {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;
}
