package com.example.certicoach.controller;

import com.example.certicoach.provider.HelloProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HelloProvider messageProvider;

    @Test
    public void testHelloEndpoint_ReturnsHelloWorld() throws Exception {
        // Arrange: Stel de verwachte boodschap in op de mock
        when(messageProvider.getMessage()).thenReturn("Hello World!");

        // Act & Assert: Verzend een GET-verzoek en controleer het resultaat
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk()) // Verwacht een 200 OK status
                .andExpect(content().string("Hello World!")); // Controleer of de inhoud klopt
    }
}