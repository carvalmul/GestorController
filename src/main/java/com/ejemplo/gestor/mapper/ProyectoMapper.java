package com.ejemplo.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.model.Proyecto;

public final class ProyectoMapper {

    // Mapper que convierte entre la capa HTTP (DTO) y la capa de negocio (modelo).
    // Aquí se "traducen" los datos para que el service no tenga que lidiar con JSON ni con request objects.
    private ProyectoMapper() {
    }

    // Crea un modelo Proyecto a partir de una petición de creación.
    public static Proyecto aModelo(ProyectoRequest peticion) {

        Proyecto proyecto = new Proyecto();

        aplicar(peticion, proyecto);
        proyecto.setActivo(true);

        return proyecto;
    }

    // Crea un modelo Proyecto a partir de una petición con cambios parciales.
    public static Proyecto aModelo(ProyectoPatchRequest cambios) {

        Proyecto proyecto = new Proyecto();

        if (cambios.getNombre() != null) {
            proyecto.setNombre(cambios.getNombre());
        }

        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }

        if (cambios.getActivo() != null) {
            proyecto.setActivo(cambios.getActivo());
        }

        return proyecto;
    }

    // Convierte un Proyecto a su representación de salida (DTO de respuesta).
    public static ProyectoResponse aRespuesta(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.isActivo(),
                proyecto.getNumeroDeIncidencias());
    }

    // Convierte una lista de modelos a una lista de respuestas HTTP.
    public static List<ProyectoResponse> aRespuestas(List<Proyecto> proyectos) {

        List<ProyectoResponse> resultado = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            resultado.add(aRespuesta(proyecto));
        }

        return resultado;
    }

    // Rellena los campos comunes desde un request completo.
    public static void aplicar(ProyectoRequest peticion, Proyecto proyecto) {

        if (peticion.getNombre() != null) {
            proyecto.setNombre(peticion.getNombre());
        }

        if (peticion.getDescripcion() != null) {
            proyecto.setDescripcion(peticion.getDescripcion());
        }
    }

    // Rellena solo los campos presentes en el patch.
    public static void aplicar(ProyectoPatchRequest cambios, Proyecto proyecto) {

        if (cambios.getNombre() != null) {
            proyecto.setNombre(cambios.getNombre());
        }

        if (cambios.getDescripcion() != null) {
            proyecto.setDescripcion(cambios.getDescripcion());
        }

        if (cambios.getActivo() != null) {
            proyecto.setActivo(cambios.getActivo());
        }
    }
}