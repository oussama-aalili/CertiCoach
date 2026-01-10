package com.example.certicoach.controller;

import com.example.certicoach.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageController messageController;

    @Test
    public void testMessageEndpoint_returnsGoedBezig() throws Exception {
        Message message = new Message();
        message.setContent("Goed bezig!");

        when(messageController.hello("3")).thenReturn("Goed bezig!");

        mockMvc.perform(get("/message/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Goed bezig!"));
    }
}

