package com.ejemplo.gestor.dto;

public record ProyectoResponse(
        Integer id,
        String nombre,
        String descripcion,
    boolean activo,
    int numeroDeIncidencias) {
}