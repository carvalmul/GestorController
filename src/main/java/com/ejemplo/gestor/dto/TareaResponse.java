package com.ejemplo.gestor.dto;

import com.ejemplo.gestor.controller.Tarea;

public record TareaResponse(
        Integer id,
        String titulo,
        String prioridad,
        Boolean completada,
        int proyectoId) {

    public static TareaResponse desde(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId()
        );
    }
}