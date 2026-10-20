package com.ejemplo.gestor.dto;

// DTO de salida para devolver una tarea al cliente.
public record TareaResponse(
        Integer id,
        String titulo,
        String prioridad,
    Boolean completada,
    int proyectoId) {
}