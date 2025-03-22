package ru.ahibis.musicreviewbot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class KorunaExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String Code;
    @Column(nullable = false)
    Long Amount;
    @Column(nullable = false)
    Double Rate;

    @Temporal(TemporalType.DATE)
    private Date date;
}
