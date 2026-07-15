package com.gustavo.posto_combustivel.exception;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        String error,
        String message
) {
}
