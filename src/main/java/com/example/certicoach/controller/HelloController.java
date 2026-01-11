package com.example.certicoach.controller;

import com.example.certicoach.provider.HelloProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final HelloProvider messageProvider;

    HelloController(HelloProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @GetMapping("/hello")
    public String hello() {
        return this.messageProvider.getMessage();
    }
}