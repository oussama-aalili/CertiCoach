package com.example.certicoach.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vraag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String vraagTekst;

    @OneToMany(
            mappedBy = "vraag",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Antwoord> antwoorden = new ArrayList<>();

    public Vraag(String vraagTekst) {
        this.vraagTekst = vraagTekst;
    }

    public void voegAntwoordToe(Antwoord antwoord) {
        antwoorden.add(antwoord);
        antwoord.setVraag(this);
    }

//    public void verwijderAntwoord(Antwoord antwoord) {
//        antwoorden.remove(antwoord);
//        antwoord.setVraag(null);
//    }

    public void setVraagTekst(String nieuweVraagTekst) {
        if (nieuweVraagTekst == null || nieuweVraagTekst.isBlank()) {
            throw new IllegalArgumentException("Vraagtekst mag niet leeg zijn");
        }
        this.vraagTekst = nieuweVraagTekst;
    }

    @ManyToMany
    @JoinTable(
            name = "vraag_leerdoel",
            joinColumns = @JoinColumn(name = "vraag_id"),
            inverseJoinColumns = @JoinColumn(name = "leerdoel_id")
    )
    private Set<Leerdoel> leerdoelen = new HashSet<>();

    public void koppelLeerdoel(Leerdoel leerdoel) {
        leerdoelen.add(leerdoel);
    }

    public void vervangLeerdoelen(Set<Leerdoel> nieuweLeerdoelen) {
        leerdoelen.clear();
        leerdoelen.addAll(nieuweLeerdoelen);
    }




}
