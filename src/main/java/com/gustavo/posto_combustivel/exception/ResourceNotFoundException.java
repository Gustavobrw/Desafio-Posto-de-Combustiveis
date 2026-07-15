package com.gustavo.posto_combustivel.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Utilizado para lançar exceções quando um recurso não é encontrado no sistema,
    // como um registro de banco de dados ausente.

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
