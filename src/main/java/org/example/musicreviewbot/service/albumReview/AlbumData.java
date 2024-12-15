package org.example.musicreviewbot.service.albumReview;

import org.example.musicreviewbot.service.yandex.Album;

import java.util.ArrayList;

public class AlbumData {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;

    public AlbumData(String title, String artist, String[] trackTitles, double[] marks) {
        this.title = title;
        this.artist = artist;
        this.trackTitles = trackTitles;
        this.marks = marks;
    }
}
