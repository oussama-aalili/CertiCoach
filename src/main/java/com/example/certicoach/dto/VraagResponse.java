package com.example.certicoach.dto;


import java.util.List;

public record VraagResponse(
        Long id,
        String vraagTekst,
        List<AntwoordResponse> antwoorden,
        List<LeerdoelResponse> leerdoelen
) {
}