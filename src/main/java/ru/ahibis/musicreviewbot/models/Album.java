package ru.ahibis.musicreviewbot.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private Long authorId;
}