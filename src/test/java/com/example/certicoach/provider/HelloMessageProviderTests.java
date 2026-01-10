package com.example.certicoach.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HelloMessageProviderTests {

    @Test
    public void testGetMessage_ReturnsHelloWorld() {
        // Arrange
        HelloProvider provider = new HelloProvider();

        // Act
        String message = provider.getMessage();

        // Assert
        assertEquals("Hello World!", message, "De boodschap moet 'Hello World!' zijn.");
    }
}