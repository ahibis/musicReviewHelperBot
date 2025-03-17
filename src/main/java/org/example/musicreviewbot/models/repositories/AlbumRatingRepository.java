package org.example.musicreviewbot.models.repositories;

import org.example.musicreviewbot.models.AlbumRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRatingRepository extends JpaRepository<AlbumRating, Long> {
}
