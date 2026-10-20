package com.ejemplo.gestor.dto;

// DTO de salida para representar un proyecto ya procesado por la API.
// Este tipo es lo que se envía al cliente al final de la cadena.
public record ProyectoResponse(
        Integer id,
        String nombre,
        String descripcion,
    boolean activo,
    int numeroDeIncidencias) {
}