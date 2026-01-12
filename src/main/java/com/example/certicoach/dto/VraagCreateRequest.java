package com.example.certicoach.dto;

import java.util.List;

public record VraagCreateRequest(
        String vraagTekst,
        List<AntwoordRequest> antwoorden,
        List<Long> leerdoelIds
) {
}
