package com.example.certicoach.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Leerdoel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titel;

    @Column(length = 2000)
    private String omschrijving;

    public Leerdoel(String titel, String omschrijving) {
        this.titel = titel;
        this.omschrijving = omschrijving;
    }

}
