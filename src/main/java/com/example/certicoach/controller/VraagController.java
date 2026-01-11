package com.example.certicoach.controller;

import com.example.certicoach.dto.VraagCreateRequest;
import com.example.certicoach.dto.VraagResponse;
import com.example.certicoach.dto.VraagUpdateRequest;
import com.example.certicoach.service.VraagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vragen")
@AllArgsConstructor
public class VraagController {

    private final VraagService vraagService;


    // Het Aanmaken van een vraag
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VraagResponse create(@RequestBody VraagCreateRequest request) {
        return vraagService.create(request);
    }

    // Het ophalen van een vraag
    @GetMapping("/{id}")
    public VraagResponse getById(@PathVariable Long id) {
        return vraagService.getById(id);
    }


    // Het ophalen van alle vragen
    @GetMapping
    public List<VraagResponse> getAll() {
        return vraagService.getAll();
    }

    // het aanpassen van een vraag
    @PutMapping("/{id}")
    public VraagResponse update(@PathVariable Long id, @RequestBody VraagUpdateRequest request) {
        return vraagService.update(id, request);
    }

    // het verwijderen van een vraag
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        vraagService.delete(id);
    }

}
