package com.example.certicoach.service;

import com.example.certicoach.dto.AntwoordRequest;
import com.example.certicoach.dto.VraagCreateRequest;
import com.example.certicoach.model.Vraag;
import com.example.certicoach.repository.LeerdoelRepository;
import com.example.certicoach.repository.VraagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VraagServiceTest {

    private final VraagRepository repo = Mockito.mock(VraagRepository.class);
    private final LeerdoelRepository repoLeerdoelen = Mockito.mock(LeerdoelRepository.class);
    private final VraagService service = new VraagService(repo, repoLeerdoelen);

    @Test
    void create_valid_callsSave() {
        // arrange: save returns the entity (good enough for this test)
        Mockito.when(repo.save(any(Vraag.class))).thenAnswer(inv -> inv.getArgument(0));

        VraagCreateRequest req = new VraagCreateRequest(
                "Wat is Scrum?",
                List.of(
                        new AntwoordRequest("Een framework", true),
                        new AntwoordRequest("Een programmeertaal", false)
                ),
                List.of()
        );


        // act
        var response = service.create(req);

        // assert
        assertNotNull(response);
        assertEquals("Wat is Scrum?", response.vraagTekst());
        assertEquals(2, response.antwoorden().size());
        Mockito.verify(repo).save(any(Vraag.class));
    }

    @Test
    void create_blankVraagTekst_throws400() {
        VraagCreateRequest req = new VraagCreateRequest(
                "   ",
                List.of(
                        new AntwoordRequest("A", true),
                        new AntwoordRequest("B", false)
                ),
                List.of(1L, 2L)
        );

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.create(req));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void create_noCorrectAnswer_throws400() {
        VraagCreateRequest req = new VraagCreateRequest(
                "Vraag?",
                List.of(
                        new AntwoordRequest("A", false),
                        new AntwoordRequest("B", false)
                ),
                List.of(1L)
        );

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.create(req));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }
}
