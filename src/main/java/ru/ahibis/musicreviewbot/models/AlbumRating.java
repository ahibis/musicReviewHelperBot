package ru.ahibis.musicreviewbot.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AlbumRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long albumId;
    private double mark;

    // Getters and Setters
}