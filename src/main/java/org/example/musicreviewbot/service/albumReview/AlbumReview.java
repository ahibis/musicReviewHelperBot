package org.example.musicreviewbot.service.albumReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class AlbumReview {
    String title;
    String artist;
    ArrayList<String> trackTitles;
    ArrayList<Double> marks;

    public AlbumReview(AlbumData data) {
        title = data.title;
        artist = data.artist;
        trackTitles = data.trackTitles;
        marks = data.marks;
    }

    double getEstimate(){
        double size = marks.size();
        double sum2 = marks.stream().mapToDouble(Double::doubleValue).map(v->v*v).sum();
        return Math.sqrt(sum2/size);
    }
    String getTracksWithMarks() {
        var result = "";
        for (int i = 0; i < trackTitles.size(); i++) {
            String mark = i< marks.size() ? String.valueOf(marks.get(i).intValue())  : "?";
            result +=  String.format("%s %s/10\n",trackTitles.get(i), mark);
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
        return String.format(albumTemplate, artist, title, getTracksWithMarks(), estimate, artist.replace(" ",""));
    }

}
