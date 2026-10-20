package com.ejemplo.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.model.Tarea;

public final class TareaMapper {

    // Mapper de tareas. Tiene la misma función que ProyectoMapper, pero para la entidad Tarea.
    // Es el puente entre JSON y la entidad del dominio.
    private TareaMapper() {
    }

    // Convierte una petición de creación en una entidad Tarea.
    public static Tarea aModelo(TareaRequest peticion) {

        Tarea tarea = new Tarea();

        aplicar(peticion, tarea);
        tarea.setCompletada(false);

        return tarea;
    }

    // Convierte una petición de edición parcial en una entidad parcialmente cargada.
    public static Tarea aModelo(TareaPatchRequest cambios) {

        Tarea tarea = new Tarea();

        if (cambios.getTitulo() != null) {
            tarea.setTitulo(cambios.getTitulo());
        }

        if (cambios.getPrioridad() != null) {
            tarea.setPrioridad(cambios.getPrioridad());
        }

        if (cambios.getCompletada() != null) {
            tarea.setCompletada(cambios.getCompletada());
        }

        return tarea;
    }

    // Convierte una tarea a formato de respuesta para devolverla por HTTP.
    public static TareaResponse aRespuesta(Tarea tarea) {
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId());
    }

    // Convierte una lista entera de tareas a una lista de respuestas HTTP.
    public static List<TareaResponse> aRespuestas(List<Tarea> tareas) {

        List<TareaResponse> resultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            resultado.add(aRespuesta(tarea));
        }

        return resultado;
    }

    // Copia los campos del patch a la entidad Tarea.
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

    // Copia los campos del request completo a la entidad Tarea.
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