package com.gustavo.posto_combustivel.exception;

public class BusinessException extends RuntimeException{

    // Utilizado para lançar exceções relacionadas a regras de negócio violadas,
    // como tentativas de realizar operações inválidas ou inconsistentes.

    public BusinessException(String message) {
        super(message);
    }
}
