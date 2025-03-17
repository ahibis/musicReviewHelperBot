package org.example.musicreviewbot.repositories;

import org.example.musicreviewbot.models.YandexAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YandexAlbumRepository extends JpaRepository<YandexAlbum, Long> {

}