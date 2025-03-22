package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.Album;
import ru.ahibis.musicreviewbot.repositories.dto.AlbumStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByAuthorIdAndTitle(Long AuthorId, String title);

    @Query("SELECT new ru.ahibis.musicreviewbot.repositories.dto.AlbumStatsDTO(" +
            "a.id, " +
            "a.title, " +
            "AVG(ar.mark)," +
            "au.name) " +
            "FROM Album a " +
            "JOIN Author au ON a.authorId = au.id " +
            "LEFT JOIN AlbumRating ar ON a.id = ar.albumId "+
            "GROUP by a.id, au.name")
    List<AlbumStatsDTO> getAlbumWithStats();



}