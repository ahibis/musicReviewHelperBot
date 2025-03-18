package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.AlbumRating;
import org.example.musicreviewbot.repositories.dto.AlbumStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRatingRepository extends JpaRepository<AlbumRating, Long> {
    Optional<AlbumRating> getByUserIdAndAlbumId(Long userId, Long albumId);

    @Query("SELECT new org.example.musicreviewbot.repositories.dto.AlbumStatsDTO(" +
            "a.id, " +
            "a.title, " +
            "ar.mark," +
            "au.name) " +
            "FROM Album a " +
            "JOIN Author au ON a.authorId = au.id " +
            "LEFT JOIN AlbumRating ar ON a.id = ar.albumId "+
            "WHERE ar.userId = :userId "+
            "GROUP by a.id, ar.mark, au.name")
    List<AlbumStatsDTO> getByUserId(@Param("userId") Long userId);
}
