package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.AlbumRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRatingRepository extends JpaRepository<AlbumRating, Long> {
    Optional<AlbumRating> getByUserIdAndAlbumId(Long userId, Long albumId);


}
