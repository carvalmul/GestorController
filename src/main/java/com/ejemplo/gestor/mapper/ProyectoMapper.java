package com.ejemplo.gestor.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.controller.Proyecto;
import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;

public final class ProyectoMapper {

    private ProyectoMapper() {
    }

    public static Proyecto aModelo(ProyectoRequest peticion) {

        Proyecto proyecto = new Proyecto();

        aplicar(peticion, proyecto);
        proyecto.setActivo(true);

        return proyecto;
    }

    public static ProyectoResponse aRespuesta(Proyecto proyecto) {
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.isActivo(),
                proyecto.getNumeroDeIncidencias());
    }

    public static List<ProyectoResponse> aRespuestas(List<Proyecto> proyectos) {

        List<ProyectoResponse> resultado = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            resultado.add(aRespuesta(proyecto));
        }

        return resultado;
    }

    public static void aplicar(ProyectoRequest peticion, Proyecto proyecto) {

        if (peticion.getNombre() != null) {
            proyecto.setNombre(peticion.getNombre());
        }

        if (peticion.getDescripcion() != null) {
            proyecto.setDescripcion(peticion.getDescripcion());
        }
    }

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