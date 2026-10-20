package com.ejemplo.gestor.error;

public class ConflictoException extends RuntimeException {

    // Se lanza cuando la operación es válida sintácticamente, pero no cumple una regla de negocio.
    // Ejemplo típico: intentar crear un proyecto con un nombre duplicado.
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}