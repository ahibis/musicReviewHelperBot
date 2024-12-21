package org.example.musicreviewbot.service.albumReview;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AlbumDTO {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;
}
