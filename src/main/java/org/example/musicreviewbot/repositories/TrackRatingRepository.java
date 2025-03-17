package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.Track;
import org.example.musicreviewbot.models.TrackRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRatingRepository extends JpaRepository<TrackRating, Long> {

    boolean existsByTrackIdAndUserId(Long trackId, Long userId);


    Optional<TrackRating> getByTrackIdAndUserId(Long trackId, Long userId);
}