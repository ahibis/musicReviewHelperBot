package org.example.musicreviewbot.service.albumReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class AlbumReview {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;

    public AlbumReview(AlbumData data) {
        title = data.title;
        artist = data.artist;
        trackTitles = data.trackTitles;
        marks = data.marks;
    }

    double getEstimate(){
        double size = marks.length;
        double sum2 = Arrays.stream(marks).map(v->v*v).sum();
        return Math.sqrt(sum2/size);
    }
    String getTracksWithMarks() {
        var result = "";
        for (int i = 0; i < trackTitles.length; i++) {
            String mark = i< marks.length ? String.valueOf((int) marks[i])  : "?";
            result +=  String.format("%s %s/10\n",trackTitles[i], mark);
        }
        return result;
    }

    public String makeReview() {
        String albumTemplate = """
                **ОБЗОР АЛЬБОМА №**
                %s - %s
                %s
                -——————————-
                ВЕСЬ АЛЬБОМ %.1f/10
                #ОбзорАльбома #%s
                """;
        double estimate = getEstimate();
        return String.format(Locale.ENGLISH, albumTemplate, artist, title, getTracksWithMarks(), estimate, artist.replace(" ",""));
    }

}
