package com.ejemplo.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.controller.Tarea;
import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;

public final class TareaMapper {

    private TareaMapper() {
    }

    public static Tarea aModelo(TareaRequest peticion) {

        Tarea tarea = new Tarea();

        aplicar(peticion, tarea);
        tarea.setCompletada(false);

        return tarea;
    }

    public static TareaResponse aRespuesta(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId());
    }

    public static List<TareaResponse> aRespuestas(List<Tarea> tareas) {

        List<TareaResponse> resultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            resultado.add(aRespuesta(tarea));
        }

        return resultado;
    }

    public static void aplicar(TareaPatchRequest cambios, Tarea tarea) {

        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }

        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }

        if (cambios.getCompletada() != null) {
            tarea.setCompletada(cambios.getCompletada());
        }
    }

    public static void aplicar(TareaRequest peticion, Tarea tarea) {

        if (peticion.getTitulo() != null) {
            tarea.setTitulo(peticion.getTitulo());
        }

        if (peticion.getPrioridad() != null) {
            tarea.setPrioridad(peticion.getPrioridad());
        }

        if (peticion.getProyectoId() != null) {
            tarea.setProyectoId(peticion.getProyectoId());
        }
    }
}