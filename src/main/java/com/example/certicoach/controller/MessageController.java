package com.example.certicoach.controller;

import com.example.certicoach.model.Message;
import com.example.certicoach.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/message/{id}")
    public String hello(@PathVariable String id) {
        Optional<Message> message = messageRepository.findById(Long.parseLong(id));
        return message.map(Message::getContent).orElse("Message not found");
    }
}