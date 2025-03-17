package org.example.musicreviewbot.models.repositories;

import org.example.musicreviewbot.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}