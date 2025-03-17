package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByAlbumId(Long albumId);

    Optional<Track> findByAlbumIdAndTitle(Long albumId, String title);


}