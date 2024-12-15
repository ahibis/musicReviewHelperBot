package org.example.musicreviewbot.service.albumReview;

import lombok.AllArgsConstructor;
import org.example.musicreviewbot.service.yandex.Album;

import java.util.ArrayList;

@AllArgsConstructor
public class AlbumData {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;
}
