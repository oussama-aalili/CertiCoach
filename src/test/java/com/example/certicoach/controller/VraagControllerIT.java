package com.example.certicoach.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VraagControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void givenVraag_WhenPostHappyFlow_ThenTeturns201() throws Exception {
        //Given
        String body = """
                {
                  "vraagTekst": "Wat is Scrum?",
                  "antwoorden": [
                    { "tekst": "Een framework", "correct": true },
                    { "tekst": "Een programmeertaal", "correct": false }
                  ]
                }
                """;

        //When
        mockMvc.perform(post("/api/vragen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                //Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.antwoorden.length()").value(2));
    }


    @Test
    void givenVraag_WhenPostUnhappyFlow_ThenTeturnsBadrequest() throws Exception {
        //Given
        String body = """
                {
                  "vraagTekst": "Wat is Scrum?",
                  "antwoorden": [
                    { "tekst": "Een framework", "correct": true }
                  ]
                }
                """;

        //When
        mockMvc.perform(post("/api/vragen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                //Then
                .andExpect(status().isBadRequest());
    }


    @Test
    void postVraag_noCorrectAnswer_returns400() throws Exception {
        String body = """
                {
                  "vraagTekst": "Vraag zonder correct antwoord?",
                  "antwoorden": [
                    { "tekst": "A", "correct": false },
                    { "tekst": "B", "correct": false }
                  ]
                }
                """;

        mockMvc.perform(post("/api/vragen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

}
