package com.ejemplo.gestor.error;

public class RecursoNoEncontradoException extends RuntimeException {

    // Se lanza cuando el service intenta obtener algo que no existe en el repositorio.
    // Por ejemplo: buscar un proyecto o tarea con un id inexistente.
    public RecursoNoEncontradoException(String recurso, Object id) {
        super("No existe " + recurso + " con id " + id);
    }
}