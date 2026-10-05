package com.ejemplo.gestor.dto;

import com.ejemplo.gestor.controller.Proyecto;

public record ProyectoResponse(
        Integer id,
        String nombre,
        String descripcion,
        boolean activo,
        int numeroDeIncidencias) {

    public static ProyectoResponse desde(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.isActivo(),
                proyecto.getNumeroDeIncidencias()
        );
    }
}