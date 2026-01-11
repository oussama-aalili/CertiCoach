package com.example.certicoach.dto;

public record AntwoordResponse(
        Long id,
        String tekst,
        boolean correct
) {
}