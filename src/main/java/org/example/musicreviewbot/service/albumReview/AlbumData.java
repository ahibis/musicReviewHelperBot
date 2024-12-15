package org.example.musicreviewbot.service.albumReview;

import org.example.musicreviewbot.service.yandex.Album;

import java.util.ArrayList;

public class AlbumData {
    String title;
    String artist;
    ArrayList<String> trackTitles;
    ArrayList<Double> marks;

    public AlbumData(String title, String artist, ArrayList<String> trackTitles, ArrayList<Double> marks) {
        this.title = title;
        this.artist = artist;
        this.trackTitles = trackTitles;
        this.marks = marks;
    }
}
