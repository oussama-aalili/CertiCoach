package com.example.certicoach.service;

import com.example.certicoach.dto.*;
import com.example.certicoach.model.Antwoord;
import com.example.certicoach.model.Vraag;
import com.example.certicoach.repository.LeerdoelRepository;
import com.example.certicoach.repository.VraagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class VraagService {

    private final VraagRepository vraagRepository;
    private final LeerdoelRepository leerdoelRepository;

    // CREATE
    public VraagResponse create(VraagCreateRequest request) {
        valideerVraag(request.vraagTekst(), request.antwoorden());
        Vraag vraag = new Vraag(request.vraagTekst());

        if (request.antwoorden() != null) {
            for (AntwoordRequest a : request.antwoorden()) {
                vraag.voegAntwoordToe(new Antwoord(a.tekst(), a.correct()));
            }
        }

        var leerdoelen = resolveLeerdoelen(request.leerdoelIds());
        vraag.vervangLeerdoelen(leerdoelen);

        Vraag saved = vraagRepository.save(vraag);
        return mapToResponse(saved);
    }

    // READ (single)
    @Transactional(readOnly = true)
    public VraagResponse getById(Long id) {
        Vraag vraag = vraagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vraag niet gevonden"));
        return mapToResponse(vraag);
    }

    // READ (list)
    @Transactional(readOnly = true)
    public List<VraagResponse> getAll() {
        return vraagRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE (replace)
    public VraagResponse update(Long id, VraagUpdateRequest request) {
        valideerVraag(request.vraagTekst(), request.antwoorden());
        Vraag vraag = vraagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vraag niet gevonden"));

        vraag.setVraagTekst(request.vraagTekst());

        // Replace antwoorden
        vraag.getAntwoorden().clear();
        if (request.antwoorden() != null) {
            for (AntwoordRequest a : request.antwoorden()) {
                vraag.voegAntwoordToe(new Antwoord(a.tekst(), a.correct()));
            }
        }

        var leerdoelen = resolveLeerdoelen(request.leerdoelIds());
        vraag.vervangLeerdoelen(leerdoelen);

        Vraag saved = vraagRepository.save(vraag);
        return mapToResponse(saved);
    }

    // DELETE
    public void delete(Long id) {
        if (!vraagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vraag niet gevonden");
        }
        vraagRepository.deleteById(id);
    }


    // Mapping helper
    private VraagResponse mapToResponse(Vraag vraag) {
        List<AntwoordResponse> antwoorden = vraag.getAntwoorden().stream()
                .map(a -> new AntwoordResponse(a.getId(), a.getTekst(), a.isCorrect()))
                .toList();


        var leerdoelen = vraag.getLeerdoelen().stream()
                .map(ld -> new LeerdoelResponse(ld.getId(), ld.getTitel()))
                .toList();

        return new VraagResponse(vraag.getId(), vraag.getVraagTekst(), antwoorden, leerdoelen);
    }


    // Bussines rulles
    private void valideerVraag(String vraagTekst, List<AntwoordRequest> antwoorden) {
        if (vraagTekst == null || vraagTekst.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "vraagTekst is verplicht");
        }

        if (antwoorden == null || antwoorden.size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimaal 2 antwoorden zijn verplicht (multiple choice)");
        }

        boolean heeftMinimaalEenCorrectAntwoord = antwoorden.stream().anyMatch(AntwoordRequest::correct);
        if (!heeftMinimaalEenCorrectAntwoord) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimaal 1 antwoord moet correct zijn");
        }

        boolean heeftLeegAntwoord = antwoorden.stream().anyMatch(a -> a.tekst() == null || a.tekst().isBlank());
        if (heeftLeegAntwoord) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Antwoord tekst mag niet leeg zijn");
        }
    }

    private java.util.Set<com.example.certicoach.model.Leerdoel> resolveLeerdoelen(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return java.util.Set.of();

        var leerdoelen = leerdoelRepository.findAllById(ids);
        // check of alles bestaat
        if (leerdoelen.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eén of meerdere leerdoelen bestaan niet");
        }
        return new java.util.HashSet<>(leerdoelen);
    }


}
