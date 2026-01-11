package com.example.certicoach.dto;

import java.util.List;

public record VraagUpdateRequest(
        String vraagTekst,
        List<AntwoordRequest> antwoorden
) {
}
