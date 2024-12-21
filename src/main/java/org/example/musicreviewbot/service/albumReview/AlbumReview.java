package org.example.musicreviewbot.service.albumReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class AlbumReview {
    String title;
    String artist;
    String[] trackTitles;
    double[] marks;

    public AlbumReview(AlbumDTO data) {
        title = data.title;
        artist = data.artist;
        trackTitles = data.trackTitles;
        marks = data.marks;
    }

    double getEstimate(){
        return Math.sqrt(Arrays.stream(marks)
                .map(v->v*v)
                .average().orElse(Double.NaN));
    }
    String getTracksWithMarks() {
        var result = new ArrayList<String>();
        for (int i = 0; i < trackTitles.length; i++) {
            String mark = i< marks.length ? String.valueOf((int) marks[i])  : "?";
            result.add(String.format("%s %s/10", trackTitles[i], mark));
        }
        return String.join("\n", result);
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
