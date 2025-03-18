package org.example.musicreviewbot.services;

import org.example.musicreviewbot.models.*;
import org.example.musicreviewbot.repositories.*;
import org.example.musicreviewbot.repositories.dto.AlbumStatsDTO;
import org.example.musicreviewbot.repositories.dto.AlbumWithTracksDTO;
import org.example.musicreviewbot.services.albumReview.AlbumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private TrackRatingRepository trackRatingRepository;

    @Autowired
    private AlbumRatingRepository albumRatingRepository;


    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public List<AlbumStatsDTO> getAllAlbums() {
        return albumRepository.getAlbumWithStats();
    }

    public Optional<AlbumWithTracksDTO> getAlbumWithTracks(Long albumId) {
        Album album = albumRepository.findById(albumId).orElse(null);
        List<Track> tracks = trackRepository.findByAlbumId(albumId);
        if(album == null) {
            return Optional.empty();
        }
        AlbumWithTracksDTO albumWithTracksDTO = new AlbumWithTracksDTO(albumId, album.getTitle(), album.getAuthorId(), tracks);
        return Optional.of(albumWithTracksDTO);
    }

    public Long saveAlbum(AlbumDTO albumDTO, Long userId) {
        Author author = authorRepository.findByName(albumDTO.getArtist())
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(albumDTO.getArtist());
                    return authorRepository.save(newAuthor);
                });


        Album album = albumRepository.findByAuthorIdAndTitle(author.getId(), albumDTO.getTitle())
                .orElseGet(() -> {
                    Album newAlbum = new Album();
                    newAlbum.setTitle(albumDTO.getTitle());
                    newAlbum.setAuthorId(author.getId());
                    return albumRepository.save(newAlbum);
                });

        double[] marks = albumDTO.getMarks();
        for (int i = 0; i < albumDTO.getTrackTitles().length; i++) {
            if(i>=marks.length) {
                continue;
            }
            String title = albumDTO.getTrackTitles()[i];
            Long albumId = album.getId();

            int mark = (int) marks[i];
            Track track = trackRepository.findByAlbumIdAndTitle(albumId, title)
                    .orElseGet(() -> {
                        Track newTrack = new Track();
                        newTrack.setTitle(title);
                        newTrack.setAlbumId(albumId);
                        newTrack.setMarkSum(0L);
                        newTrack.setMarkCount(0L);

                        return trackRepository.save(newTrack);
                    });
            track.setMarkCount((track.getMarkCount()==null? 0: track.getMarkCount()) + 1L);
            track.setMarkSum((track.getMarkSum()==null? 0: track.getMarkSum()) + (long) mark);
            trackRepository.save(track);

            Long trackId = track.getId();


            TrackRating trackRating = trackRatingRepository
                    .getByTrackIdAndUserId(trackId, userId)
                    .orElseGet(TrackRating::new);
            trackRating.setTrackId(trackId);
            trackRating.setUserId(userId);
            trackRating.setMark(mark);
            trackRatingRepository.save(trackRating);
        }
        return album.getId();
    }

    public void markAlbum(Long albumId, double mark, Long userId) {
        AlbumRating rating = albumRatingRepository
                .getByUserIdAndAlbumId(userId, albumId)
                .orElseGet(AlbumRating::new);
        rating.setUserId(userId);
        rating.setMark(mark);
        rating.setAlbumId(albumId);
        albumRatingRepository.save(rating);
    }

    public List<AlbumStatsDTO> getAlbumReviews(Long userId) {
        return albumRatingRepository.getByUserId(userId);
    }



}