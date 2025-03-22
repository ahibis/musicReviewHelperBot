package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.TrackRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRatingRepository extends JpaRepository<TrackRating, Long> {

    boolean existsByTrackIdAndUserId(Long trackId, Long userId);


    Optional<TrackRating> getByTrackIdAndUserId(Long trackId, Long userId);
}