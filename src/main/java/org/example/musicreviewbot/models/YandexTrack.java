package org.example.musicreviewbot.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class YandexTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long albumId;
    private Long albumYandexId;

    // Getters and Setters
}