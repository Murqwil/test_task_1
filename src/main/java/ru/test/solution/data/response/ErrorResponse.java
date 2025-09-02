package ru.test.solution.data.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp,
        String path
) {
    public ErrorResponse(String message, String path) {
        this(message, LocalDateTime.now(), path);
    }
}