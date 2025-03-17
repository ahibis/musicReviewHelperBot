package org.example.musicreviewbot.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class YandexAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long albumId;
    private Long albumYandexId;

    // Getters and Setters
}