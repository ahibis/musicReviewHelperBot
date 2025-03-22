package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.YandexTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YandexTrackRepository extends JpaRepository<YandexTrack, Long> {


}
