package org.example.musicreviewbot.models.repositories;

import org.example.musicreviewbot.models.YandexTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YandexTrackRepository extends JpaRepository<YandexTrack, Long> {
}
