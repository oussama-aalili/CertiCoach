package com.example.certicoach.controller;

import com.example.certicoach.provider.HelloProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HelloControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloProvider messageProvider; // Echte bean wordt geïnjecteerd

    @Test
    public void testHelloEndpoint_ReturnsActualMessage() throws Exception {
        String expectedMessage = messageProvider.getMessage();

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}