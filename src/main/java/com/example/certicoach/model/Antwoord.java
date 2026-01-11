package com.example.certicoach.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Antwoord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String tekst;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vraag_id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private Vraag vraag;

    public Antwoord(String tekst, boolean correct) {
        this.tekst = tekst;
        this.correct = correct;
    }

}
