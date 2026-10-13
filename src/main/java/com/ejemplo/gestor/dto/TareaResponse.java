package com.ejemplo.gestor.dto;

public record TareaResponse(
        Integer id,
        String titulo,
        String prioridad,
    Boolean completada,
    int proyectoId) {
}