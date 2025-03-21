package ru.ahibis.musicreviewbot.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long albumId;
    private Long markSum;
    private Long markCount;

    // Getters and Setters
}